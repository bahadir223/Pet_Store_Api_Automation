package api.test;

import api.endpoints.PetEndPoints;
import api.endpoints.Routes;
import api.payload.Pet;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class Pet_Tests {

    Faker faker;
    Pet petPayload;
    String randomStatus;
    int createdId;

    /*
    Bu metod, testlerden önce çalıştırılan bir @BeforeClass anotasyonuna sahip kurulum metodudur.
    Amacı, testlerde kullanılacak olan Pet nesnesini sahte verilerle doldurup hazır hale getirmektir.
     */
    @BeforeTest
    public void setupData() {
        faker = new Faker();
        petPayload = new Pet();

        petPayload.setId(faker.idNumber().hashCode());
        petPayload.setName(faker.name().name());
        List<String> photoUrls = new ArrayList<>();
        photoUrls.add(faker.internet().avatar()); // Faker'dan rastgele bir avatar URL'si alabilir
        photoUrls.add(faker.internet().url());    // Rastgele bir URL alabilir
        petPayload.setPhotoUrls(photoUrls);
        List<String> statuses = List.of("available", "pending", "sold");
        randomStatus = statuses.get(faker.random().nextInt(statuses.size()));
        petPayload.setStatus(randomStatus);
    }

    @Test
    public void testPostPet() {
        Response response = PetEndPoints.createPet(petPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

        // Yanıtın JSON'dan ID'yi alıyoruz
        createdId = response.jsonPath().getInt("id");
        System.out.println("Created ID for test: " + createdId);
    }


    @Test(dependsOnMethods = {"testPostPet"})
    public void testGetPet() {
        System.out.println("Fetching Pet with ID: " + createdId);

        Response response = PetEndPoints.readPet(createdId);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(dependsOnMethods = {"testGetPet"})
    public void testUpdatePet() {
        petPayload.setName(faker.name().name()); // Yeni bir isim veriyoruz

        // Pet'i güncelleme
        Response response = PetEndPoints.updatePet(petPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

        // Güncellenen pet'i kontrol edelim
        Response responseAfterUpdate = PetEndPoints.readPet(createdId); // Burada createdId'yi kullanıyoruz
        responseAfterUpdate.then().log().all();
        Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
        Assert.assertEquals(responseAfterUpdate.getBody().jsonPath().getString("name"), petPayload.getName()); // Güncel isim kontrolü
    }

    @Test(dependsOnMethods = {"testUpdatePet"})
    public void testDeletePetById() throws InterruptedException {
        System.out.println("Deleting Pet with ID: " + createdId);

        // Pet'i silme işlemi
        Response deleteResponse = PetEndPoints.deletePet(this.petPayload.getId());
        deleteResponse.then().log().all();
        Assert.assertEquals(deleteResponse.getStatusCode(), 200, "Silme isteği başarısız.");
        // Silinmiş pet'e tekrar erişmeye çalışalım
        //Response response = PetEndPoints.readPet(this.petPayload.getId());
        //int statusCode = response.getStatusCode(); // Durum kodunu alıyoruz
        //// Silinmiş pet'e erişimin 404 dönmesini bekliyoruz
        //Assert.assertEquals(statusCode, 404, "Silinmiş bir pet'e erişim için 404 bekleniyor.");

    }


}
