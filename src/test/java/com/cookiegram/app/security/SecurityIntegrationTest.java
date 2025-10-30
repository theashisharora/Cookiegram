package com.cookiegram.app.security;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.cookiegram.app.config.SecurityConfig;
import com.cookiegram.app.controllers.DashboardController;
import com.cookiegram.app.controllers.HomeController;
import com.cookiegram.app.services.PromotionService;

/**
 * This test loads only the web layer + security,
 * not the entire app context.
 *
 * We mock PromotionService because HomeController needs it.
 */
@WebMvcTest(controllers = {
        DashboardController.class,
        HomeController.class,
        SecurityConfig.class
})
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // HomeController depends on PromotionService, so we fake it:
    @MockBean
    private PromotionService promotionService;

    @Test
    void landingPage_isPublic() throws Exception {
        when(promotionService.getActivePromotions())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void loginPage_isPublic() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    void employeePage_requiresAuth() throws Exception {
        mockMvc.perform(get("/employee"))
                .andExpect(status().is3xxRedirection()) // 302 -> /login
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "bob", roles = {"EMPLOYEE"})
    void employeePage_allowsEmployee() throws Exception {
        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "bob", roles = {"EMPLOYEE"})
    void adminPage_blocksEmployee() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden()); // 403
    }

    @Test
    @WithMockUser(username = "alice", roles = {"ADMIN"})
    void adminPage_allowsAdmin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }
}
