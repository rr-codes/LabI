# EECS 1021 Lab I

(c) 2021 Richard Robinson and James Andrew Smith

----

**IMPORTANT**: Your JavaFX installation must be version **15.0.1** and be located in your **home directory**.

----


## Part A

In Part A, you will be creating a graph in JavaFX which graphs points of an arbitrary quadratic formula, of the form `ax^2 + bx + c`.

The `Main` class is the class which creates the graph. It relies on its controller, a `FormulaController`, which provides it data.
The `QuadraticCoefficients` class is a simple model to encapsulate the three quadratic coefficient values.

Your task is to implement all the methods in `FormulaController`, as indicated by the `TODO`s. The JavaDoc comments
of all the methods specify their implementation. The methods involve either calculations or parsing.

## Part B

In Part B, you will again be creating a JavaFX graph. However, this graph will graph the values of your Arduino's potentiometer
in real time. The x-axis of the graph is time (specifically, milliseconds since the _Unix epoch_, Jan 1, 1970). The y-values represent the raw value
of the potentiometer, which are in the range `[0, 1023]`.

The Graph logic is already available in Part B's `Main` class. Your task is to implement the 
`serialEvent` method in the `DataController` class.

Specifically, the method should be implemented in the following way:
1. If the event type of the `serialPortEvent` event is not equal to `SerialPort.LISTENING_EVENT_DATA_RECEIVED`,
the method should end (return).
2. The raw bytes received should be received using the `getReceivedData` method of `serialPortEvent`, and assigned to 
a variable.
3. The raw bytes should be converted to an integer. Assuming the variable of the raw bytes is named `data`, then this can be done
using `ByteBuffer.wrap(data).getInt()`.
4. Get the current time, using `System.currentTimeMillis()`.
5. Create a data point, by creating a new instance of `XYChart.Data<Number, Number>`.
The first parameter of the constructor should be the value from (4), and the second parameter should be the value from (3).
5. Add the new data point variable to the `dataPoints` list. Assuming the instance you created in (5) is called `dataPoint`,
you can do this by using `Platform.runLater(() -> this.dataPoints.add(dataPoint));`
(The reason `Platform.runLater` is used in this particular case involves multithreading, and is beyond the scope of this course.)
   
## Part C

In Part C, you will be receiving data from the potentiometer again. However, this time instead of a graph, you will be
using a JavaFX TableView.

Instead of creating the logic in a controller as you have previously, you will be creating the TableView yourself in this part.

### A bit about _Generics_

Generics are a crucial concept in programming. Suppose you have a `List` class. Instead of having to make a separate
List class for each type, such as `IntList`, `StringList`, etc, we can use generics.

```java
class MyList<T> {
    public T getFirstElement() {
        // whatever
    }
}
```

Now, if you want a list of Strings, you can just do `var aList = new MyList<String>()`. Now, `aList` is a list of Strings.
The generic type `T` represents whatever type it needs to be; in this case, `T` would be `String`. Consequently,
the method `getFirstElemet` would return a `T`, so a `String`.

Sometimes, Java is smart enough to not have to explicitly specify the types, and so they can be inferred. 
In these cases, the types can be omitted, and an empty set of angle brackets (`<>`) can be used instead.
This is known as _generic type inference_.

JavaFX uses extensive uses of generics.

------

In Part C, you will have to implement the `getTableView` method, using the following implementation.
1. Create an instance of `TableView<XYChart.Data<Number, Number>>`. The generic parameter, `XYChart.Data<Number, Number>`,
represents the type of class that is the model for each row. In our case, each row does indeed represent
a singular data point.
   - Note that the inner `<Number, Number>` generics are generic types for the `XYChart.Data` class itself.
    They represent the type of the x values, and the y values, respectively. In our case, both are `Number`s. 
   - `Number` is an abstract class, of which specific number types such as `Integer` and `Double` inherit from.
    
2. Create a new `TableColumn<XYChart.Data<Number, Number>, Number>` instance. The first generic parameter, `XYChart.Data<Number, Number>`,
again represents the model class for each row. The second generic parameter, `Number`, represents the type of value in this specific
column, which is a `Number`. The name of the column should be called something like `"Date"` or `"Time"`,
as this is the timestamp for each data point.

3. Set the _cell value factory_ of the column you just created. The cell value factory determines how the specific value for the column should be extracted
from the row. For example, `column.setCellValueFactory(row -> row.getValue.getFooProperty())`.
   
4. Create a date format instance using `DateFormat.getTimeInstance();`

5. Create a converter, to convert between the number of milliseconds and a more human readable timestamp.
This can be done by creating a new `FormatStringConverter<Number>` and specifying the date format created in (4).
   
6. Set the _cell factory_ of the column to specify this converter. This is done via `column.setCellFactory(c -> new TextFieldDataCell<>(converter))`
(where `column` is the name of the column variable you created, and `converter` is the name of the converter you created in (5)).

7. Repeat steps 2 and 3 for the second column, the value column.

8. Create a list of all the columns using the `List.of()` method. Then, set the columns of the table using 
`table.getColumns().setAll(list)`, where `table` is the name of your table and `list` is the name of the list you just created.
   
9. Return the table!