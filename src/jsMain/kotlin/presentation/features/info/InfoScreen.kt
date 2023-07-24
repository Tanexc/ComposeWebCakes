package presentation.features.info

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.use_case.GetUserIdUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import presentation.features.info.components.InfoCard
import presentation.style.ui.theme.getTheme

@Composable
fun InfoScreen() {
    val theme = getTheme(true)
    val getUserIdUseCase = GetUserIdUseCase()

    val userId = remember { mutableStateOf("") }



    getUserIdUseCase().onEach {
        userId.value = it
    }.launchIn(CoroutineScope(Dispatchers.Default))


    val state = rememberLazyListState()
    Box(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .align(Alignment.Center),
            state = state
        ) {
            item {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                ) {
                    Row(modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.Center)
                    ) {
                        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                            InfoCard(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .fillMaxWidth(0.5f)
                                    .height(324.dp),
                                borderRadius = 16.dp,
                                backgroundColor = theme.secondaryContainer.copy(0.3f)
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                                ) {
                                    Text(
                                        userId.value,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                    )
                                }

                            }
                        }

                        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                            InfoCard(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .fillMaxWidth()
                                    .height(324.dp),
                                borderRadius = 16.dp,
                                backgroundColor = theme.secondaryContainer.copy(0.3f)
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                                ) {
                                    Text(
                                        "INFOCARD",
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }

        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = state
            )
        )
    }

}
