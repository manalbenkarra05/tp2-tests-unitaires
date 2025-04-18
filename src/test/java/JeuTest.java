import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.example.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JeuTest {

    @Mock private Banque banque;
    @Mock private Joueur joueur;
    @Mock private De de1;
    @Mock private De de2;

    @InjectMocks private Jeu jeu;

    @BeforeEach
    public void init() {
        jeu.fermer(); // on ferme pour le test n°4
    }

    // 4. ✅ Cas où le jeu est fermé
    @Test
    public void testJeuFerme_Exception() {
        assertThrows(JeuFermeException.class, () -> {
            jeu.jouer(joueur, de1, de2);
        });
    }

    // 5. ✅ Cas joueur insolvable
    @Test
    public void testJoueurInsolvable_neTouchePasAuxDes() throws Exception {
        jeu = new Jeu(banque); // rouvrir un jeu valide

        when(joueur.mise()).thenReturn(50);
        doThrow(new DebitImpossibleException("solde insuffisant")).when(joueur).debiter(50);

        jeu.jouer(joueur, de1, de2);

        // ✅ Vérifie que les dés ne sont jamais utilisés
        verify(de1, never()).lancer();
        verify(de2, never()).lancer();
    }

    // 6. ✅ Cas joueur gagne avec somme = 7
    @Test
    public void testGagnantAvec7_fermeSiBanqueInsolvable() throws Exception {
        jeu = new Jeu(banque);

        when(joueur.mise()).thenReturn(100);
        when(de1.lancer()).thenReturn(3);
        when(de2.lancer()).thenReturn(4); // somme = 7
        when(banque.est_solvable()).thenReturn(false); // banque devient insolvable

        jeu.jouer(joueur, de1, de2);

        // ✅ Vérifie que la banque crédite la mise
        verify(banque).crediter(100);

        // ✅ Vérifie que le joueur gagne
        verify(joueur).crediter(200);
        verify(banque).debiter(200);

        // ✅ Le jeu se ferme
        assertFalse(jeu.estOuvert());
    }

    // 7. ✅ Test avec une vraie banque (implémentation concrète)
    @Test
    public void testGagnantAvecBanqueReelleEtFermeture() throws Exception {
        Banque banqueReelle = new BanqueConcrète(150, 140); // 🔧 min plus élevé
        Jeu jeuReel = new Jeu(banqueReelle);

        Joueur joueur = mock(Joueur.class);
        when(joueur.mise()).thenReturn(30);

        De de1 = mock(De.class);
        De de2 = mock(De.class);
        when(de1.lancer()).thenReturn(3);
        when(de2.lancer()).thenReturn(4); // total = 7

        jeuReel.jouer(joueur, de1, de2);

        assertFalse(jeuReel.estOuvert());
    }

}
