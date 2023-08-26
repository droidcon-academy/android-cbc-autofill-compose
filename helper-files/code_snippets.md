# Code snippets for each change
## AutofillScreenDemo.kt

```kotlin

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

// inside the AutofillDemo composable

val autofill = LocalAutofill.current

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

```