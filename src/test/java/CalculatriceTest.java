import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.Calculatrice;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CalculatriceTest {

    @Mock
    private Calculatrice calculatrice;

    @Test
    public void testAdditionner() {
        // Arrange : simulation du comportement de la méthode "additionner"
        when(calculatrice.additionner(2, 3)).thenReturn(5);

        // Act : appel simulé (le vrai code n'est pas exécuté)
        int resultat = calculatrice.additionner(2, 3);

        // Assert : vérification du résultat retourné
        assertEquals(5, resultat);

        // Vérification que la méthode a bien été appelée avec 2 et 3
        verify(calculatrice).additionner(2, 3);

        // Vérification qu'aucune autre méthode n’a été appelée après
        verifyNoMoreInteractions(calculatrice);

    }
}
