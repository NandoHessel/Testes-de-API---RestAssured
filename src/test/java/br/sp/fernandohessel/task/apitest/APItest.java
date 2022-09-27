package br.sp.fernandohessel.task.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APItest {

    @BeforeClass
    public static void Setup() {
        RestAssured.baseURI = ("http://localhost:8001/tasks-backend");
    }

    @Test
    public void DeveRetornarTarefasAgendadas() {
        RestAssured
                .given()
                .when()
                    .get("/todo")
                .then()
                    .statusCode(200);
    }

    @Test
    public void DeveAdicionarTarefaComSucesso() {
        RestAssured
                .given()
                    .body("{ \"task\": \"TesteViaAPI\", \"dueDate\": \"2022-12-20\" }")
                    .contentType(ContentType.JSON)
                .when()
                    .post("/todo")
                .then()
                    .log().all()
                    .statusCode(201);
    }

    @Test
    public void NaoDeveAdicionarTarefaInvalida() {
        RestAssured
                .given()
                    .body("{ \"task\": \"TesteViaAPI\", \"dueDate\": \"2020-12-20\" }")
                    .contentType(ContentType.JSON)
                .when()
                    .post("/todo")
                .then()
                    .log().all()
                    .statusCode(400)
                    .body("message", CoreMatchers.is("Due date must not be in past"));
    }
}
