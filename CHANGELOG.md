# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.0.1] - 2025-07-22

### Added

- Initial release of the JSON library for GWT.
- Added `JSONArray` class with the following functionalities:
    - Create empty arrays or arrays from JSON strings.
    - Get elements by index for various data types.
    - Add elements to the array for various data types.
    - Check if an element is null and get the length of the array.
    - Remove elements from the array.
    - Convert the array to a string.
- Added `JSONObject` class with the following functionalities:
    - Create empty objects or objects from JSON strings.
    - Get values by key for various data types.
    - Add key-value pairs to the object for various data types.
    - Check if a key exists and if a value is null.
    - Get the set of keys and convert the object to a string.
- Added `JSONException` class for handling JSON-related errors.
- Added comprehensive test suites for `JSONArray` and `JSONObject` classes, covering basic functionality, data types, edge cases, concurrency, complex types, and error handling.
- Added GWT module configuration file (`JSON.gwt.xml`) specifying dependencies and settings for the GWT module.

### Fixed

- No fixes in this release as it is the initial version.

### Changed

- No changes in this release as it is the initial version.

### Deprecated

- No deprecations in this release as it is the initial version.

### Removed

- No removals in this release as it is the initial version.

### Security

- No security-related changes in this release as it is the initial version.
