import static XlsxSpec.xlsx

xlsx ("test.xlsx") {
   sheet (0) {
        row (0) {
            cell {
                value = 1
            }
            cell {
                idx = 3
                value = "test"
                style {
                    backgroundColor = "red"
                    color = "white"
                }
            }
            cell {
                idx = 5
                value = "TEST"
                style {
                    backgroundColor = "light_blue"
                    color = "dark_blue"
                }
            }

       }
       row (1) {
           cell {
               idx = 2
               value = "123456"
           }
           cell {
               idx = 4
               value = "654321"
               style {
                   backgroundColor = "tan"
                   color = "yellow"
               }
           }

       }
    }
}
