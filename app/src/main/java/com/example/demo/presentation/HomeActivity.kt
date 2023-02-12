package com.example.demo.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.demo.R
import com.example.demo.presentation.HomeActivity.Companion.DIALOG_TEST_TAG
import com.example.demo.presentation.HomeActivity.Companion.LIST_TEST_TAG
import com.example.demo.presentation.HomeActivity.Companion.LOADING_TEST_TAG
import com.example.demo.presentation.base.BaseActivity
import com.example.demo.presentation.utils.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    companion object {
        const val LOADING_TEST_TAG = "LOADING_TEST_TAG"
        const val LIST_TEST_TAG = "LIST_TEST_TAG"
        const val DIALOG_TEST_TAG = "DIALOG_TEST_TAG"
    }

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.handleIntent(HomeIntent.GetDomains)
    }

    @Composable
    override fun ScreenView() {
        HomeScreen(viewModel.state, viewModel, onDismiss = {
            finish()
        })
    }

}

@Composable
fun HomeScreen(state: HomeState, viewModel: HomeViewModel, onDismiss: () -> Unit) {

    Screen(
        titleView = {
            Text(
                text = stringResource(id = R.string.title),
                style = heading_3b.copy(white1000),
                textAlign = TextAlign.Center
            )
        },
        content = {
            when (state) {
                is HomeState.Loading -> {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .testTag(LOADING_TEST_TAG),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is HomeState.DomainReceivedState -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag(LIST_TEST_TAG)
                    ) {
                        items(state.domains,
                            key = {
                                it.domainUrl
                            },
                            itemContent = { model ->
                                Card(
                                    modifier = Modifier.padding(MaterialTheme.dimens.paddingExtraSmall),
                                    shape = RoundedCornerShape(5),
                                    border = BorderStroke(1.dp, grey100)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(MaterialTheme.dimens.paddingSmall),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        AsyncImage(
                                            modifier = Modifier.size(MaterialTheme.dimens.iconExtraLarge),
                                            model = model.domainIcon,
                                            contentDescription = "Async Image"
                                        )
                                        Spacer(modifier = Modifier.width(width = MaterialTheme.dimens.paddingSmall))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = model.name,
                                                style = heading_2a.copy(blueGrey900)
                                            )
                                            Text(
                                                text = model.domainUrl,
                                                style = heading_4b.copy(blueGrey900)
                                            )
                                            Text(
                                                text = viewModel.getLatencyForDomain(model.domainUrl),
                                                style = heading_4b.copy(blueGrey500)
                                            )
                                        }
                                    }

                                }
                            })
                    }
                }
                is HomeState.Error -> {
                    ErrorDialog(
                        handleError = state.exception.message,
                        onDismiss = {
                            onDismiss.invoke()
                        })
                }
            }
        }
    )
}


@Composable
fun ErrorDialog(handleError: String?, onDismiss: () -> Unit) {
    if (handleError != null) {
        AlertDialog(
            modifier = Modifier.testTag(DIALOG_TEST_TAG),
            onDismissRequest = onDismiss,
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.error_encountered),
                        style = heading_2a.copy(blueGrey900)
                    )
                }
            }, text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = handleError,
                        style = heading_3b.copy(blueGrey900),
                        textAlign = TextAlign.Center
                    )
                }
            }, buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onDismiss.invoke() }
                    ) {
                        Text("Dismiss")
                    }
                }
            })
    }
}
