package cart.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dto.ProductResponseDto;
import cart.entity.ProductEntity;
import cart.service.CartService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class CartApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    void insertTest() throws Exception {
        when(cartService.addProduct(any())).thenReturn(new ProductEntity(1, "name", 1000, "image"));
        final String imageUrl = "http://www.test.image.png";

        this.mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"name\", \"image\": \"" + imageUrl + "\", \"price\": \"1000\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void insertTest_imageNonUrl_fail() throws Exception {
        this.mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"name\", \"image\": \"image\", \"price\": \"1000\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void insertTest_priceNonNumber_fail() throws Exception {
        final String imageUrl = "http://www.test.image.png";

        this.mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"name\", \"image\": \"" + imageUrl + "\", \"price\": \"1000\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTest() throws Exception {
        when(cartService.getProducts()).thenReturn(List.of(
                new ProductResponseDto(1, "image1", "name1", 1000),
                new ProductResponseDto(2, "image2", "name2", 2000)));

        this.mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("name1"))
                .andExpect(jsonPath("$[0].price").value("1000"))
                .andExpect(jsonPath("$[0].image").value("image1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("name2"))
                .andExpect(jsonPath("$[1].price").value("2000"))
                .andExpect(jsonPath("$[1].image").value("image2"));
    }

    @Test
    void updateTest() throws Exception {
        final String imageUrl = "http://www.test.image.png";

        this.mockMvc.perform(put("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"1\", \"name\": \"name\", \"image\": \"" + imageUrl + "\", \"price\": \"1000\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateTest_imageNonUrl_fail() throws Exception {
        this.mockMvc.perform(put("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"1\", \"name\": \"name\", \"image\": \"image\", \"price\": \"1000\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateTest_priceNonNumber_fail() throws Exception {
        final String imageUrl = "http://www.test.image.png";

        this.mockMvc.perform(put("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"1\", \"name\": \"name\", \"image\": \"" + imageUrl + "\", \"price\": \"abc\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteTest() throws Exception {
        this.mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTest_idNonNumber_fail() throws Exception {
        this.mockMvc.perform(delete("/products/a"))
                .andExpect(status().isBadRequest());
    }
}
