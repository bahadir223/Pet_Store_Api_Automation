package api.endpoints;

    /*
    Swagger URI --> https://petstore.swagger.io

    Create User(Post) -->   https://petstore.swagger.io/v2/user
    Get User(Get) -->       https://petstore.swagger.io/v2/user/{username}
    Update User(Put) -->    https://petstore.swagger.io/v2/user/{username}
    Delete User(Delete) --> https://petstore.swagger.io/v2/user/{username}

    Create Store(Post) -->  https://petstore.swagger.io/v2/store/order
    Get Store(Get) -->      https://petstore.swagger.io/v2/store/inventory
    Delete Store(Delete) -->https://petstore.swagger.io/v2/store/order/{orderId}

    Create Pet(Post) -->    https://petstore.swagger.io/v2/pet
    Get Pet(Get) -->        https://petstore.swagger.io/v2/pet/{petId}
    Update Pet(Put) -->     https://petstore.swagger.io/v2/pet
    Delete User(Delete) --> https://petstore.swagger.io/v2/pet/{petId}

     */

public class Routes {

    public static String base_url = "https://petstore.swagger.io/v2";

    //User module

    //Store module

    //Pet module
    public static String post_url = base_url + "/pet";
    public static String get_url = base_url + "/pet/{petId}";
    public static String update_url = base_url + "/pet";
    public static String delete_url = base_url + "/pet/{petId}";
}
