package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Ecosistema;
import com.mycompany.myapp.repository.EcosistemaRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EcosistemaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EcosistemaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ecosistemas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EcosistemaRepository ecosistemaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEcosistemaMockMvc;

    private Ecosistema ecosistema;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ecosistema createEntity(EntityManager em) {
        Ecosistema ecosistema = new Ecosistema().nombre(DEFAULT_NOMBRE);
        return ecosistema;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ecosistema createUpdatedEntity(EntityManager em) {
        Ecosistema ecosistema = new Ecosistema().nombre(UPDATED_NOMBRE);
        return ecosistema;
    }

    @BeforeEach
    public void initTest() {
        ecosistema = createEntity(em);
    }

    @Test
    @Transactional
    void createEcosistema() throws Exception {
        int databaseSizeBeforeCreate = ecosistemaRepository.findAll().size();
        // Create the Ecosistema
        restEcosistemaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecosistema)))
            .andExpect(status().isCreated());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeCreate + 1);
        Ecosistema testEcosistema = ecosistemaList.get(ecosistemaList.size() - 1);
        assertThat(testEcosistema.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void createEcosistemaWithExistingId() throws Exception {
        // Create the Ecosistema with an existing ID
        ecosistema.setId(1L);

        int databaseSizeBeforeCreate = ecosistemaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEcosistemaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecosistema)))
            .andExpect(status().isBadRequest());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = ecosistemaRepository.findAll().size();
        // set the field null
        ecosistema.setNombre(null);

        // Create the Ecosistema, which fails.

        restEcosistemaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecosistema)))
            .andExpect(status().isBadRequest());

        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEcosistemas() throws Exception {
        // Initialize the database
        ecosistemaRepository.saveAndFlush(ecosistema);

        // Get all the ecosistemaList
        restEcosistemaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ecosistema.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    void getEcosistema() throws Exception {
        // Initialize the database
        ecosistemaRepository.saveAndFlush(ecosistema);

        // Get the ecosistema
        restEcosistemaMockMvc
            .perform(get(ENTITY_API_URL_ID, ecosistema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ecosistema.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    void getNonExistingEcosistema() throws Exception {
        // Get the ecosistema
        restEcosistemaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEcosistema() throws Exception {
        // Initialize the database
        ecosistemaRepository.saveAndFlush(ecosistema);

        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();

        // Update the ecosistema
        Ecosistema updatedEcosistema = ecosistemaRepository.findById(ecosistema.getId()).get();
        // Disconnect from session so that the updates on updatedEcosistema are not directly saved in db
        em.detach(updatedEcosistema);
        updatedEcosistema.nombre(UPDATED_NOMBRE);

        restEcosistemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEcosistema.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEcosistema))
            )
            .andExpect(status().isOk());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
        Ecosistema testEcosistema = ecosistemaList.get(ecosistemaList.size() - 1);
        assertThat(testEcosistema.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void putNonExistingEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();
        ecosistema.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcosistemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ecosistema.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecosistema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();
        ecosistema.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecosistema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();
        ecosistema.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecosistema)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEcosistemaWithPatch() throws Exception {
        // Initialize the database
        ecosistemaRepository.saveAndFlush(ecosistema);

        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();

        // Update the ecosistema using partial update
        Ecosistema partialUpdatedEcosistema = new Ecosistema();
        partialUpdatedEcosistema.setId(ecosistema.getId());

        partialUpdatedEcosistema.nombre(UPDATED_NOMBRE);

        restEcosistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcosistema.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEcosistema))
            )
            .andExpect(status().isOk());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
        Ecosistema testEcosistema = ecosistemaList.get(ecosistemaList.size() - 1);
        assertThat(testEcosistema.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void fullUpdateEcosistemaWithPatch() throws Exception {
        // Initialize the database
        ecosistemaRepository.saveAndFlush(ecosistema);

        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();

        // Update the ecosistema using partial update
        Ecosistema partialUpdatedEcosistema = new Ecosistema();
        partialUpdatedEcosistema.setId(ecosistema.getId());

        partialUpdatedEcosistema.nombre(UPDATED_NOMBRE);

        restEcosistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcosistema.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEcosistema))
            )
            .andExpect(status().isOk());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
        Ecosistema testEcosistema = ecosistemaList.get(ecosistemaList.size() - 1);
        assertThat(testEcosistema.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void patchNonExistingEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();
        ecosistema.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcosistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ecosistema.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ecosistema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();
        ecosistema.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ecosistema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEcosistema() throws Exception {
        int databaseSizeBeforeUpdate = ecosistemaRepository.findAll().size();
        ecosistema.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcosistemaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ecosistema))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ecosistema in the database
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEcosistema() throws Exception {
        // Initialize the database
        ecosistemaRepository.saveAndFlush(ecosistema);

        int databaseSizeBeforeDelete = ecosistemaRepository.findAll().size();

        // Delete the ecosistema
        restEcosistemaMockMvc
            .perform(delete(ENTITY_API_URL_ID, ecosistema.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ecosistema> ecosistemaList = ecosistemaRepository.findAll();
        assertThat(ecosistemaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
