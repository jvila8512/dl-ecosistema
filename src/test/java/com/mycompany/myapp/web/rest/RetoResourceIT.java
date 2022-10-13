package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Reto;
import com.mycompany.myapp.repository.RetoRepository;
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
 * Integration tests for the {@link RetoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RetoResourceIT {

    private static final String DEFAULT_RETO = "AAAAAAAAAA";
    private static final String UPDATED_RETO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/retos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RetoRepository retoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRetoMockMvc;

    private Reto reto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reto createEntity(EntityManager em) {
        Reto reto = new Reto().reto(DEFAULT_RETO).descripcion(DEFAULT_DESCRIPCION);
        return reto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reto createUpdatedEntity(EntityManager em) {
        Reto reto = new Reto().reto(UPDATED_RETO).descripcion(UPDATED_DESCRIPCION);
        return reto;
    }

    @BeforeEach
    public void initTest() {
        reto = createEntity(em);
    }

    @Test
    @Transactional
    void createReto() throws Exception {
        int databaseSizeBeforeCreate = retoRepository.findAll().size();
        // Create the Reto
        restRetoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reto)))
            .andExpect(status().isCreated());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeCreate + 1);
        Reto testReto = retoList.get(retoList.size() - 1);
        assertThat(testReto.getReto()).isEqualTo(DEFAULT_RETO);
        assertThat(testReto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createRetoWithExistingId() throws Exception {
        // Create the Reto with an existing ID
        reto.setId(1L);

        int databaseSizeBeforeCreate = retoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRetoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reto)))
            .andExpect(status().isBadRequest());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRetoIsRequired() throws Exception {
        int databaseSizeBeforeTest = retoRepository.findAll().size();
        // set the field null
        reto.setReto(null);

        // Create the Reto, which fails.

        restRetoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reto)))
            .andExpect(status().isBadRequest());

        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = retoRepository.findAll().size();
        // set the field null
        reto.setDescripcion(null);

        // Create the Reto, which fails.

        restRetoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reto)))
            .andExpect(status().isBadRequest());

        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRetos() throws Exception {
        // Initialize the database
        retoRepository.saveAndFlush(reto);

        // Get all the retoList
        restRetoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reto.getId().intValue())))
            .andExpect(jsonPath("$.[*].reto").value(hasItem(DEFAULT_RETO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getReto() throws Exception {
        // Initialize the database
        retoRepository.saveAndFlush(reto);

        // Get the reto
        restRetoMockMvc
            .perform(get(ENTITY_API_URL_ID, reto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reto.getId().intValue()))
            .andExpect(jsonPath("$.reto").value(DEFAULT_RETO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingReto() throws Exception {
        // Get the reto
        restRetoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReto() throws Exception {
        // Initialize the database
        retoRepository.saveAndFlush(reto);

        int databaseSizeBeforeUpdate = retoRepository.findAll().size();

        // Update the reto
        Reto updatedReto = retoRepository.findById(reto.getId()).get();
        // Disconnect from session so that the updates on updatedReto are not directly saved in db
        em.detach(updatedReto);
        updatedReto.reto(UPDATED_RETO).descripcion(UPDATED_DESCRIPCION);

        restRetoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReto))
            )
            .andExpect(status().isOk());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
        Reto testReto = retoList.get(retoList.size() - 1);
        assertThat(testReto.getReto()).isEqualTo(UPDATED_RETO);
        assertThat(testReto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingReto() throws Exception {
        int databaseSizeBeforeUpdate = retoRepository.findAll().size();
        reto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRetoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReto() throws Exception {
        int databaseSizeBeforeUpdate = retoRepository.findAll().size();
        reto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRetoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReto() throws Exception {
        int databaseSizeBeforeUpdate = retoRepository.findAll().size();
        reto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRetoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRetoWithPatch() throws Exception {
        // Initialize the database
        retoRepository.saveAndFlush(reto);

        int databaseSizeBeforeUpdate = retoRepository.findAll().size();

        // Update the reto using partial update
        Reto partialUpdatedReto = new Reto();
        partialUpdatedReto.setId(reto.getId());

        restRetoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReto))
            )
            .andExpect(status().isOk());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
        Reto testReto = retoList.get(retoList.size() - 1);
        assertThat(testReto.getReto()).isEqualTo(DEFAULT_RETO);
        assertThat(testReto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateRetoWithPatch() throws Exception {
        // Initialize the database
        retoRepository.saveAndFlush(reto);

        int databaseSizeBeforeUpdate = retoRepository.findAll().size();

        // Update the reto using partial update
        Reto partialUpdatedReto = new Reto();
        partialUpdatedReto.setId(reto.getId());

        partialUpdatedReto.reto(UPDATED_RETO).descripcion(UPDATED_DESCRIPCION);

        restRetoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReto))
            )
            .andExpect(status().isOk());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
        Reto testReto = retoList.get(retoList.size() - 1);
        assertThat(testReto.getReto()).isEqualTo(UPDATED_RETO);
        assertThat(testReto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingReto() throws Exception {
        int databaseSizeBeforeUpdate = retoRepository.findAll().size();
        reto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRetoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReto() throws Exception {
        int databaseSizeBeforeUpdate = retoRepository.findAll().size();
        reto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRetoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReto() throws Exception {
        int databaseSizeBeforeUpdate = retoRepository.findAll().size();
        reto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRetoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(reto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reto in the database
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReto() throws Exception {
        // Initialize the database
        retoRepository.saveAndFlush(reto);

        int databaseSizeBeforeDelete = retoRepository.findAll().size();

        // Delete the reto
        restRetoMockMvc
            .perform(delete(ENTITY_API_URL_ID, reto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reto> retoList = retoRepository.findAll();
        assertThat(retoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
