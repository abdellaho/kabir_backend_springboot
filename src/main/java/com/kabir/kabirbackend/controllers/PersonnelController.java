package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.config.requests.AuthRequest;
import com.kabir.kabirbackend.config.requests.RefreshToken;
import com.kabir.kabirbackend.config.responses.LoginResponse;
import com.kabir.kabirbackend.config.responses.ValidationResponse;
import com.kabir.kabirbackend.config.security.JwtUtil;
import com.kabir.kabirbackend.config.security.encryption.Encryption;
import com.kabir.kabirbackend.config.util.PasswordGenerator;
import com.kabir.kabirbackend.dto.AbsenceDTO;
import com.kabir.kabirbackend.dto.PersonnelDTO;
import com.kabir.kabirbackend.entities.Etablissement;
import com.kabir.kabirbackend.repository.EtablissementRepository;
import com.kabir.kabirbackend.service.PersonnelService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/personnel")
class PersonnelController {

    private final Logger logger = LoggerFactory.getLogger(PersonnelController.class);
    private static final ResourceBundle bundleFR = ResourceBundle.getBundle("i18n/ApplicationResources", Locale.of("fr"));

    private final PersonnelService personnelService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final EtablissementRepository etablissementRepository;

    PersonnelController(PersonnelService personnelService,
                        AuthenticationManager authenticationManager,
                        JwtUtil jwtUtil, PasswordEncoder passwordEncoder,
                        EtablissementRepository etablissementRepository
    ) {
        this.personnelService = personnelService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.etablissementRepository = etablissementRepository;
    }

    /*
    getAll: `${BASE_URL}/personnel`,
    getAllAlowed: `${BASE_URL}/personnel/allowed`,
    getById: (id: bigint) => `${BASE_URL}/personnel/${id}`,
    create: `${BASE_URL}/personnel`,
    update: (id: bigint) => `${BASE_URL}/personnel/${id}`,
    delete: (id: bigint) => `${BASE_URL}/personnel/${id}`,
    exist: `${BASE_URL}/personnel/exist`,
    search: `${BASE_URL}/personnel/search`,
    present: `${BASE_URL}/personnel/present`

    present(dateAbsence: Date): Observable<Personnel[]> {
    let body = { dateAbsence };
    return this.http.post<Personnel[]>(ENDPOINTS.PERSONNEL.present, body);
  }

    id: bigint | null;
    designation: string;
    cin: string;
    login: string;
    password: string;
    typePersonnel: number;
    etatComptePersonnel: boolean;
    tel1: string;
    tel2: string;
    adresse: string;
    email: string;
    dateEntrer: Date;
    dateSuppression: Date | null;
    salaire: number;
    archiver: boolean;
    supprimer: boolean;
    consulterStock: boolean;
    ajouterStock: boolean;
    modifierStock: boolean;
    supprimerStock: boolean;
    consulterRepertoire: boolean;
    ajouterRepertoire: boolean;
    modifierRepertoire: boolean;
    supprimerRepertoire: boolean;
     */

    @GetMapping
    public ResponseEntity<List<PersonnelDTO>> getAll() {
        logger.info("Fetching all personnel");
        try {
            List<PersonnelDTO> personnel = personnelService.findAll();
            return ResponseEntity.ok(personnel);
        } catch (Exception e) {
            logger.error("Error fetching all personnel: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/all-except-admin")
    public ResponseEntity<List<PersonnelDTO>> getAllExceptAdmin(@RequestBody PersonnelDTO personnelDTO) {
        logger.info("Fetching all personnel except admins");
        try {
            List<PersonnelDTO> personnel = personnelService.findAllExceptAdmin(personnelDTO);
            return ResponseEntity.ok(personnel);
        } catch (Exception e) {
            logger.error("Error fetching all personnel except admins: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/allowed")
    public ResponseEntity<List<PersonnelDTO>> getAllAllowed(@RequestBody PersonnelDTO personnelDTO) {
        logger.info("Fetching all allowed personnel");
        try {
            List<PersonnelDTO> personnel = personnelService.search(personnelDTO);
            return ResponseEntity.ok(personnel);
        } catch (Exception e) {
            logger.error("Error fetching all allowed personnel: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonnelDTO> getById(@PathVariable Long id) {
        logger.info("Fetching personnel with id: {}", id);
        try {
            PersonnelDTO personnel = personnelService.findById(id);
            updatePasswordFake(personnel);
            return ResponseEntity.ok(personnel);
        } catch (Exception e) {
            logger.error("Error fetching personnel with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<PersonnelDTO> create(@RequestBody PersonnelDTO personnelDTO) {
        logger.info("Creating personnel: {}", personnelDTO);
        try {
            PersonnelDTO createdPersonnel = personnelService.save(personnelDTO);
            return ResponseEntity.ok(createdPersonnel);
        } catch (Exception e) {
            logger.error("Error creating personnel: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PersonnelDTO> update(@PathVariable Long id, @RequestBody PersonnelDTO personnelDTO) {
        logger.info("Updating personnel with id: {}", id);
        try {
            PersonnelDTO updatedPersonnel = personnelService.save(personnelDTO);
            return ResponseEntity.ok(updatedPersonnel);
        } catch (Exception e) {
            logger.error("Error updating personnel with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonnelDTO> delete(@PathVariable Long id) {
        logger.info("Deleting personnel with id: {}", id);
        try {
            personnelService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting personnel with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody PersonnelDTO personnelDTO) {
        logger.info("Checking if personnel exists: {}", personnelDTO);
        try {
            List<PersonnelDTO> personnel = personnelService.search(personnelDTO);
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(personnel));
        } catch (Exception e) {
            logger.error("Error checking if personnel exists: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/existing")
    public ResponseEntity<ValidationResponse> existing(@RequestBody PersonnelDTO personnelDTO) {
        logger.info("Checking if personnel exists with this criteria: {}", personnelDTO);
        try {
            return ResponseEntity.ok(personnelService.existingTest(personnelDTO));
        } catch (Exception e) {
            logger.error("Error checking if personnel exists with this criteria: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<PersonnelDTO>> search(@RequestBody PersonnelDTO personnelDTO) {
        logger.info("Searching personnel: {}", personnelDTO);
        try {
            List<PersonnelDTO> personnel = personnelService.searchBySupprimerOrArchiver(personnelDTO);
            return ResponseEntity.ok(personnel);
        } catch (Exception e) {
            logger.error("Error searching personnel: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/present")
    public ResponseEntity<List<PersonnelDTO>> present(@RequestBody AbsenceDTO absenceDTO) {
        logger.info("Presenting personnel: {}", absenceDTO);
        try {
            List<PersonnelDTO> personnel = personnelService.notInAbsenceAtDate(absenceDTO);
            return ResponseEntity.ok(personnel);
        } catch (Exception e) {
            logger.error("Error presenting personnel: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthRequest request) {
        logger.info("Logging in: {}", request);
        LoginResponse loginResponse = authenticate(request, null);
        String responseMessage = Optional.ofNullable(loginResponse)
                .map(LoginResponse::message)
                .filter(StringUtils::isNotBlank)
                .orElse(bundleFR.getString("verifierLoginPassword"));
        if(null == loginResponse || null == loginResponse.user()) throw new BadCredentialsException(responseMessage);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<LoginResponse> register(@RequestBody AuthRequest request) {
        logger.info("Registering: {}", request);
        try {
            if(personnelService.count() == 0) {
                PersonnelDTO personnelDTO = new PersonnelDTO();

                personnelDTO.setLogin(request.email());
                personnelDTO.setEmail(request.email());
                personnelDTO.setPassword(passwordEncoder.encode(request.password()));
                personnelDTO.setTypePersonnel(1);
                personnelDTO.setDesignation("Administrateur");
                personnelDTO.setEtatComptePersonnel(true);

                personnelDTO = personnelService.save(personnelDTO);

                LoginResponse loginResponse = authenticate(request, personnelDTO);
                if(null == loginResponse) ResponseEntity.badRequest().build();

                return ResponseEntity.ok(loginResponse);
            } else {
                logger.error("Database has already an admin");
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            logger.error("Error registering: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/auth/admin-exist")
    public ResponseEntity<Boolean> adminExist() {
        logger.info("Checking if an admin exists");
        try {
            PersonnelDTO personnelDTO = new PersonnelDTO();
            personnelDTO.setTypePersonnel(0);
            List<PersonnelDTO> list = personnelService.search(personnelDTO);
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(list));
        } catch (Exception e) {
            logger.error("Error checking if an admin exists: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/auth/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody RefreshToken refreshToken) {
        logger.info("Refreshing token");
        try {
            String token = jwtUtil.generateToken(jwtUtil.getUsernameFromToken(refreshToken.refreshToken()));
            return ResponseEntity.ok(new LoginResponse(token, token, jwtUtil.getJwtExpirationMs(), null, ""));
        } catch (Exception e) {
            logger.error("Error refreshing token: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshToken refreshToken) {
        logger.info("Logging out");
        try {
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error logging out: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    private LoginResponse authenticate(AuthRequest request, PersonnelDTO personnelDTO) {
        logger.info("Authenticating user: {}", request.email());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email().trim(), request.password().trim()));
            if(null == personnelDTO) {
                personnelDTO = personnelService.findByEmail(request.email().trim());
            }
            String token = jwtUtil.generateToken(request.email());

            if(null == personnelDTO) return null;

            if(personnelDTO.isPasswordReset()) {
                personnelDTO.setPasswordReset(false);
                updatePasswordFake(personnelDTO);
                personnelService.save(personnelDTO);
            }

            boolean hasRights = personnelService.hasAnyRights(personnelDTO);
            if(personnelDTO.isEtatComptePersonnel()) {
                if(hasRights || personnelDTO.getTypePersonnel() == 1) {
                    updatePasswordFake(personnelDTO);

                    return new LoginResponse(token, token, jwtUtil.getJwtExpirationMs(), personnelDTO, "");
                } else {
                    return new LoginResponse("", "", 0, null, bundleFR.getString("accountWithoutRights"));
                }
            } else {
                return new LoginResponse("", "", 0, null, bundleFR.getString("accountDisabled"));
            }
        } catch (BadCredentialsException e) {
            Etablissement etablissement = null;
            if(null == personnelDTO) {
                personnelDTO = personnelService.findByEmail(request.email().trim());
            }
            if(null != personnelDTO) {
                boolean canSendEmail = false;
                List<Etablissement> list = etablissementRepository.findAll();
                if(CollectionUtils.isNotEmpty(list)) {
                    etablissement = list.getFirst();
                    if(null != etablissement.getHostMail() && !etablissement.getHostMail().isEmpty()
                            && null != etablissement.getEmail() && !etablissement.getEmail().isEmpty()
                            && null != etablissement.getPaswordMail() && !etablissement.getPaswordMail().isEmpty()
                            && etablissement.getPort() > 0) {
                        canSendEmail = true;
                    }
                }

                if(personnelDTO.getTypePersonnel() != 1) {
                    if(canSendEmail) {
                        if(!personnelDTO.isPasswordReset()) {
                            String custom = new PasswordGenerator.Builder()
                                    .length(8)
                                    .withUppercase(true)
                                    .withSpecial(false)
                                    .build()
                                    .generate();
                            personnelDTO.setPasswordFake(custom);
                            personnelDTO.setPasswordReset(true);
                            personnelService.save(personnelDTO);

                            personnelService.sendEmail(etablissement, personnelDTO);
                            return new LoginResponse("", "", 0, null, bundleFR.getString("verifierLoginPassword"));
                        }
                    } else {
                        if(personnelDTO.isEtatComptePersonnel()) {
                            personnelDTO.setEtatComptePersonnel(false);
                            personnelService.save(personnelDTO);
                        } else {
                            return new LoginResponse("", "", 0, null, bundleFR.getString("accountDisabled"));
                        }
                    }
                }
            }

            return new LoginResponse("", "", 0, null, bundleFR.getString("verifierLoginPassword"));
        } catch (Exception e) {
            logger.error("Error authenticating user: {}", e.getMessage());
            return new LoginResponse("", "", 0, null, e.getMessage());
        }
    }

    @PostMapping("/auth/me")
    public ResponseEntity<LoginResponse> getCurrentUser(@RequestBody RefreshToken refreshToken) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            if(null != refreshToken && null != refreshToken.refreshToken() && StringUtils.isNotBlank(refreshToken.refreshToken())) {
                // Try to authenticate using the refresh token
                try {
                    String email = jwtUtil.getUsernameFromToken(refreshToken.refreshToken());
                    PersonnelDTO personnelDTO = personnelService.findByEmail(email);

                    if (personnelDTO != null) {
                        String newToken = jwtUtil.generateToken(email);
                        return ResponseEntity.ok(new LoginResponse(newToken, newToken, jwtUtil.getJwtExpirationMs(), personnelDTO, ""));
                    }
                } catch (Exception e) {
                    return ResponseEntity.status(401).build();
                }
            }
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        // Extract username/email from auth (adjust based on your JWT claims/user details)
        String email = auth.getName(); // Typically email or username from JWT principal

        PersonnelDTO personnelDTO = personnelService.findByEmail(email);
        if (personnelDTO == null) {
            return ResponseEntity.status(404).build();
        }

        String newToken = jwtUtil.generateToken(email);
        return ResponseEntity.ok(new LoginResponse(newToken, newToken, jwtUtil.getJwtExpirationMs(), personnelDTO, ""));
    }

    @PostMapping("/auth/mon-compte")
    public ResponseEntity<LoginResponse> getMyAccount(@RequestBody PersonnelDTO personnelDTO) {
        logger.info("Updating personnel account of this user: {}", personnelDTO);
        try {
            PersonnelDTO updatedPersonnel = personnelService.save(personnelDTO);
            updatePasswordFake(updatedPersonnel);

            String token = jwtUtil.generateToken(updatedPersonnel.getEmail());
            return ResponseEntity.ok(new LoginResponse(token, token, jwtUtil.getJwtExpirationMs(), updatedPersonnel, ""));
        } catch (Exception e) {
            logger.error("Error updating personnel account: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    private void updatePasswordFake(PersonnelDTO personnelDTO) {
        if(StringUtils.isNotBlank(personnelDTO.getPassword()) && StringUtils.isNotBlank(personnelDTO.getEmail())) {
            personnelDTO.setPasswordFake(Encryption.strDecrypt(personnelDTO.getPassword(), 7));
        }
    }


}
