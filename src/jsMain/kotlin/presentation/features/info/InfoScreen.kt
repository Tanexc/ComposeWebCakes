package presentation.features.info

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.isSystemInDarkTheme
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
import io.ktor.util.logging.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import presentation.features.info.components.InfoCard
import presentation.style.ui.theme.getTheme

@Composable
fun InfoScreen() {
    val theme = getTheme(isSystemInDarkTheme())
    val getUserIdUseCase = GetUserIdUseCase()

    val userId = remember { mutableStateOf("") }



        getUserIdUseCase().onEach {
            KtorSimpleLogger("cum").info("yse")
            userId.value = it
        }.launchIn(CoroutineScope(Dispatchers.Default))


    Box {
        val state = rememberLazyListState()
        LazyColumn(
            modifier = Modifier,
            state = state
        ) {
            item {
                Box(modifier = Modifier
                    .fillMaxWidth()
                ) {
                    Row(modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.Center)
                    ) {
                        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                            InfoCard(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .width(720.dp)
                                    .height(324.dp),
                                borderRadius = 16.dp,
                                backgroundColor = theme.secondaryContainer.copy(0.3f)
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxSize()
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
                                    .width(720.dp)
                                    .height(324.dp),
                                borderRadius = 16.dp,
                                backgroundColor = theme.secondaryContainer.copy(0.3f)
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxSize()
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
            item {
                Box(modifier = Modifier
                    .fillMaxWidth()
                ) {
                    Row(modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.Center)
                    ) {
                        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                            InfoCard(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .width(720.dp)
                                    .height(324.dp),
                                borderRadius = 16.dp,
                                backgroundColor = theme.secondaryContainer.copy(0.3f)
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxSize()
                                ) {
                                    Text(
                                        "INFOCARD",
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
                                    .width(720.dp)
                                    .height(324.dp),
                                borderRadius = 16.dp,
                                backgroundColor = theme.secondaryContainer.copy(0.3f)
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxSize()
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
            item {
                Box(modifier = Modifier
                    .fillMaxWidth()
                ) {
                    Row(modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.Center)
                    ) {
                        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                            InfoCard(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .width(720.dp)
                                    .height(324.dp),
                                borderRadius = 16.dp,
                                backgroundColor = theme.secondaryContainer.copy(0.3f)
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxSize()
                                ) {
                                    Text(
                                        "INFOCARD",
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
                                    .width(720.dp)
                                    .height(324.dp),
                                borderRadius = 16.dp,
                                backgroundColor = theme.secondaryContainer.copy(0.3f)
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxSize()
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
            item {
                Box(modifier = Modifier
                    .fillMaxWidth()
                ) {
                    Row(modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.Center)
                    ) {
                        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                            InfoCard(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .width(720.dp)
                                    .height(324.dp),
                                borderRadius = 16.dp,
                                backgroundColor = theme.secondaryContainer.copy(0.3f)
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxSize()
                                ) {
                                    Text(
                                        "INFOCARD",
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
                                    .width(720.dp)
                                    .height(324.dp),
                                borderRadius = 16.dp,
                                backgroundColor = theme.secondaryContainer.copy(0.3f)
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxSize()
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
            item {
                Box(modifier = Modifier
                    .fillMaxWidth()
                ) {
                    Row(modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.Center)
                    ) {
                        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                            InfoCard(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .width(720.dp)
                                    .height(324.dp),
                                borderRadius = 16.dp,
                                backgroundColor = theme.secondaryContainer.copy(0.3f)
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxSize()
                                ) {
                                    Text(
                                        "INFOCARD",
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
                                    .width(720.dp)
                                    .height(324.dp),
                                borderRadius = 16.dp,
                                backgroundColor = theme.secondaryContainer.copy(0.3f)
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxSize()
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
