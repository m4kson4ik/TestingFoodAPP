package com.example.features.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.features.FoodViewModel
import com.example.features.R
import com.example.features.State
import com.example.features.models.CategoryUI
import com.example.features.models.ProductUI

@Composable
fun MainWindowUI() {
    MainWindowUI(viewModel())
}


@Composable
private fun MainWindowUI(vm : FoodViewModel) {
    val listState = rememberLazyListState()
    Scaffold(topBar = {
        CustomTopBar()
    }, bottomBar = {NavigationMenu()}) { pad ->
        val categories by vm.categories.collectAsState()
        val products by vm.products.collectAsState()
        Column(Modifier.padding(pad)) {
            MainContentProducts(products, categories)
        }
    }
}

@Composable
private fun Banners() {
    Box(modifier = Modifier.animateContentSize(animationSpec = tween(300))) {
        LazyRow() {
            items(3) {
                Image(
                    painter = painterResource(id = R.drawable.banner),
                    contentDescription = "",
                    Modifier
                        .width(300.dp)
                        .height(150.dp)
                        .clickable { })
            }
        }
    }
}

@Composable
private fun ItemsCategoryRowList(state : State<CategoryUI>) {
     if (state.data != null) {
         Box(Modifier.background(Color.White)) {
             LazyRow() {
                 items(state.data) { item ->
                     ItemCategory(item)
                 }
             }
         }
     }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainContentProducts(state : State<ProductUI>, stateCategoryUI: State<CategoryUI>) {
     if (state.data != null) {
         LazyColumn() {
             item {
                 Banners()
             }
             stickyHeader {
                 ItemsCategoryRowList(state = stateCategoryUI)
             }
             items(state.data) { item ->
                 ItemProduct(item)
             }
         }
     }
}

@Composable
fun ItemProduct(item : ProductUI) {
    Row(
        Modifier
            .padding(12.dp)
            .fillMaxSize()
            .clickable { }) {
        item.strMealThumb?.let {
            var isImageVisible by remember { mutableStateOf(true) }
            if (isImageVisible) {
                AsyncImage(
                    model = it,
                    onState = { state ->
                        if (state is AsyncImagePainter.State.Error) {
                            isImageVisible = false
                        }
                    },
                    contentDescription = "",
                    modifier = Modifier.size(135.dp)
                )
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Column(
            Modifier
                .fillMaxSize()
                .clickable { }) {
            Text(item.strMeal.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(
                "${item.strIngredient1}${
                    item.strIngredient11?.let {
                        if (it != "") {
                            ", $it"
                        } else " "
                    }
                }${
                    item.strIngredient12?.let {
                        if (it != "") {
                            ", $it"
                        } else " "
                    }
                }${
                    item.strIngredient13?.let {
                        if (it != "") {
                            ", $it"
                        } else " "
                    }
                }${
                    item.strIngredient14?.let {
                        if (it != "") {
                            ", $it"
                        } else " "
                    }
                }${
                    item.strIngredient15?.let {
                        if (it != "") {
                            ", $it"
                        } else " "
                    }
                }${
                    item.strIngredient16?.let {
                        if (it != "") {
                            ", $it"
                        } else " "
                    }
                }", fontWeight = FontWeight.Light
            )
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, Color(253, 58, 105, 255))
                ) {
                    Text("от 345р", color = Color(253, 58, 105, 255))
                }
            }
        }
    }
}

@Composable
fun ItemCategory(item : CategoryUI) {
    var selected by rememberSaveable {
        mutableStateOf(false)
    }
    Spacer(modifier = Modifier.padding(5.dp))
    ElevatedAssistChip(onClick = {selected = !selected}, colors = AssistChipDefaults.assistChipColors(
        containerColor = if (selected) Color(252, 156, 179, 255) else Color.White,
    ), label = {Text(text = item.strCategory, color = if (selected) Color(253, 58, 105, 255) else Color.Gray)})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar() {
    TopAppBar(title = {
        Row {
            Text("Москва")
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "")
        }
    }, actions = { IconButton(onClick = { /*TODO*/ }) {
        Icon(imageVector = ImageVector.vectorResource(R.drawable.qr_code__1__1), contentDescription = "")
    }})
}

@Composable
fun NavigationMenu() {
    NavigationBar {
        NavigationBarItem(selected = false, onClick = {  }, icon = { Icon(
            imageVector = ImageVector.vectorResource(R.drawable.food),
            contentDescription = "", tint = Color(253, 58, 105, 255)
        ) }, label = {Text("Меню", color = Color(253, 58, 105, 255))})
        NavigationBarItem(selected = false, onClick = {  }, icon = { Icon(
            imageVector = ImageVector.vectorResource(R.drawable.union),
            contentDescription = ""
        ) }, label = {Text("Профиль")})
        NavigationBarItem(selected = false, onClick = {  }, icon = { Icon(
            imageVector = ImageVector.vectorResource(R.drawable.basket),
            contentDescription = "",
        ) }, label = {Text("Корзина")})
    }
}