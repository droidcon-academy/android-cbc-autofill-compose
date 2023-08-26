package com.droidcon.autofillcompose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AutofillDemo(modifier: Modifier = Modifier) {
    val username = remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
    }
    val phonenumber = remember {
        mutableStateOf("")
    }

    val autofill = LocalAutofill.current

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Username", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(10.dp))

        AutofillTextField(autoFillTypes = listOf(AutofillType.Username), onFilled = {
            username.value = it
        }) {autofillNode ->
            OutlinedTextField(modifier = Modifier.onFocusChanged {focusState ->
                autofill?.run {
                    if (focusState.isFocused){
                        requestAutofillForNode(autofillNode)
                    }else{
                        cancelAutofillForNode(autofillNode)
                    }
                }
            }, value = username.value,
                onValueChange = { username.value = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                placeholder = { Text(text = "John Doe") })
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Email",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(10.dp))

        AutofillTextField(autoFillTypes = listOf(AutofillType.EmailAddress),
            onFilled = {email.value = it}) { autofillNode ->
            OutlinedTextField(modifier = Modifier.onFocusChanged {focusState->
                     autofill?.run {
                         if (focusState.isFocused){
                             requestAutofillForNode(autofillNode)
                         }else{
                             cancelAutofillForNode(autofillNode)
                         }
                     }
            }, value = email.value,
                onValueChange = {
                    email.value = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                placeholder = { Text(text = "johndoe@gmail.com") })
        }
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Phonenumber",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(10.dp))
        AutofillTextField(autoFillTypes = listOf(AutofillType.PhoneNumber), onFilled = {
            phonenumber.value = it
        }) {autofillNode ->
            OutlinedTextField(modifier = Modifier.onFocusChanged {focusState ->
                autofill?.run {
                    if (focusState.isFocused){
                        requestAutofillForNode(autofillNode)
                    }else{
                        cancelAutofillForNode(autofillNode)
                    }
                }
            }, value = phonenumber.value,
                onValueChange = {  phonenumber.value = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                placeholder = { Text(text = "+11302010101") })
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun AutofillTextField(autoFillTypes:List<AutofillType>,
                               onFilled:(String)->Unit,
                               content:@Composable (AutofillNode)->Unit){
    val autofillNode = AutofillNode(onFill = onFilled,
        autofillTypes = autoFillTypes)
    val autofillTree = LocalAutofillTree.current
    autofillTree+= autofillNode
    Box(modifier = Modifier.onGloballyPositioned {
        autofillNode.boundingBox= it.boundsInWindow()
    }){
        content(autofillNode)
    }
}