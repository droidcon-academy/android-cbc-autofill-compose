# Solution for Coding challenge

The question was to add an edit text that takes in phone number and enable autofill for that edit text

```kotlin
// inside the AutofillDemo composable
val phonenumber = remember {
    mutableStateOf("")
}

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
        
```
