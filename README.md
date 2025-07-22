# json-gwt

A GWT-compatible JSON library providing familiar org.json API for client-side JavaScript applications.

## Classes

- org.json.JSONArray
- org.json.JSONObject

## Use with maven

#### Add repository
```xml
<repositories>
  <repository>
    <id>github</id>
    <name>GitHub Packages</name>
    <url>https://maven.pkg.github.com/divroll/json-gwt</url>
  </repository>
</repositories>
```

#### Add dependency

```xml
<dependency>
   <groupId>com.divroll</groupId>
   <artifactId>json-gwt</artifactId>
   <version>0</version>
</dependency>
```

## Features

- **GWT Compatible**: Uses Google Web Toolkit's JSON client library under the hood
- **Familiar API**: Provides the same interface as the popular org.json library
- **Type Safety**: Supports all common Java types including primitives, wrappers, and BigDecimal/BigInteger
- **Null Handling**: Proper null value handling with JSONNull support
- **Nested Objects**: Full support for nested JSONObjects and JSONArrays

## Usage Examples

### Creating and Using JSONObject

```java
import org.json.JSONObject;
import org.json.JSONArray;

// Create a new JSONObject
JSONObject person = new JSONObject();
person.put("name", "John Doe");
person.put("age", 30);
person.put("active", true);

// Parse from JSON string
JSONObject parsed = new JSONObject("{\"name\":\"Jane\",\"age\":25}");

// Get values
String name = person.getString("name");
Integer age = person.getInt("age");
Boolean active = person.getBoolean("active");

// Handle null values safely
person.put("address", (String) null); // Stores as JSONNull
String address = person.getString("address"); // Returns null
```

### Creating and Using JSONArray

```java
import org.json.JSONArray;
import org.json.JSONObject;

// Create a new JSONArray
JSONArray numbers = new JSONArray();
numbers.put(1);
numbers.put(2.5);
numbers.put("three");

// Add objects
JSONObject item = new JSONObject();
item.put("id", 1);
numbers.put(item);

// Get values by index
int first = numbers.getInt(0);
double second = numbers.getDouble(1);
String third = numbers.getString(2);
JSONObject fourth = numbers.getJSONObject(3);

// Check array properties
int length = numbers.length();
boolean empty = numbers.isEmpty();
boolean isNull = numbers.isNull(0);
```

### Working with Nested Structures

```java
// Create nested structure
JSONObject user = new JSONObject();
user.put("id", 123);
user.put("name", "Alice");

JSONArray hobbies = new JSONArray();
hobbies.put("reading");
hobbies.put("coding");
user.put("hobbies", hobbies);

JSONObject address = new JSONObject();
address.put("street", "123 Main St");
address.put("city", "Anytown");
user.put("address", address);

// Access nested data
String city = user.getJSONObject("address").getString("city");
String firstHobby = user.getJSONArray("hobbies").getString(0);

// Convert to JSON string
String jsonString = user.toString();
```

### Supported Data Types

The library supports all common Java data types:

- **Primitives**: `boolean`, `int`, `long`, `float`, `double`
- **Wrappers**: `Boolean`, `Integer`, `Long`, `Float`, `Double`
- **Strings**: `String`
- **Big Numbers**: `BigDecimal`, `BigInteger`
- **JSON Types**: `JSONObject`, `JSONArray`, `JSONNull`
- **Generic Objects**: Via `put(String, Object)` and `get(String)` methods

### Error Handling

```java
try {
    JSONObject obj = new JSONObject();
    obj.put("number", 42);
    
    // This will throw JSONException if the value is not a boolean
    boolean value = obj.getBoolean("number");
} catch (JSONException e) {
    // Handle parsing errors
}
```

## GWT Module Configuration

Add the following to your `.gwt.xml` module file:

```xml
<inherits name='org.JSON'/>
```

## Requirements

- Google Web Toolkit (GWT) 2.8.0 or later
- Java 8 or later

## License

This software is distributed under the Apache License 2.0. See the LICENSE file for full details.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## Support

For issues and questions, please use the GitHub issue tracker.