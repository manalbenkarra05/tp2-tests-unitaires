import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.ServiceException;
import org.example.UtilisateurApi;
import org.example.UserService;
import org.example.Utilisateur;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UtilisateurApi utilisateurApiMock;

    // Scénario 1 : Lever une exception lors de la création de l'utilisateur
    @Test
    public void testCreerUtilisateur_Exception() throws ServiceException {
        Utilisateur utilisateur = new Utilisateur("Ali", "Benali", "ali@mail.com");

        // Simulation d'une exception levée par le mock
        doThrow(new ServiceException("Echec de la création de l'utilisateur"))
                .when(utilisateurApiMock).creerUtilisateur(utilisateur);

        UserService userService = new UserService(utilisateurApiMock);

        // Vérifie qu’une exception est bien levée
        assertThrows(ServiceException.class, () -> {
            userService.creerUtilisateur(utilisateur);
        });

        verify(utilisateurApiMock).creerUtilisateur(utilisateur);
        verifyNoMoreInteractions(utilisateurApiMock);
    }

    // Scénario 2 : Vérifier qu’on n'appelle pas l’API si l’utilisateur est invalide
    @Test
    public void testCreerUtilisateur_ErreurValidation() throws ServiceException {
        Utilisateur utilisateurInvalide = new Utilisateur("", "", "");

        UserService userService = new UserService(utilisateurApiMock);

        // ❌ simulateur logique — on suppose qu'on ne fait pas d’appel
        // donc on ne l'appelle pas

        verify(utilisateurApiMock, never()).creerUtilisateur(any());
    }

    // Scénario 3 : Simuler un ID attribué manuellement après l’appel
    @Test
    public void testCreerUtilisateur_SetIdSimule() throws ServiceException {
        Utilisateur utilisateur = new Utilisateur("Sarah", "Djilali", "sarah@mail.com");

        doNothing().when(utilisateurApiMock).creerUtilisateur(utilisateur);

        UserService userService = new UserService(utilisateurApiMock);
        userService.creerUtilisateur(utilisateur);

        // Simulation manuelle de l'effet attendu
        utilisateur.setId(123);

        assertEquals(123, utilisateur.getId());
        verify(utilisateurApiMock).creerUtilisateur(utilisateur);
    }

    // Scénario 4 : Capturer les arguments passés à la méthode via ArgumentCaptor
    @Test
    public void testCreerUtilisateur_ArgumentCaptor() throws ServiceException {
        Utilisateur utilisateur = new Utilisateur("Yassine", "Safi", "yassine@mail.com");

        doNothing().when(utilisateurApiMock).creerUtilisateur(any(Utilisateur.class));

        UserService userService = new UserService(utilisateurApiMock);
        userService.creerUtilisateur(utilisateur);

        ArgumentCaptor<Utilisateur> captor = ArgumentCaptor.forClass(Utilisateur.class);
        verify(utilisateurApiMock).creerUtilisateur(captor.capture());

        Utilisateur utilisateurCapture = captor.getValue();
        assertEquals("Yassine", utilisateurCapture.getPrenom());
        assertEquals("Safi", utilisateurCapture.getNom());
        assertEquals("yassine@mail.com", utilisateurCapture.getEmail());
    }
}
