package com.adresse.vue;

import com.adresse.manager.ManagerUtilisateur;
import com.adresse.model.Utilisateur;
import org.springframework.security.crypto.bcrypt.BCrypt;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Locale;
import com.adresse.regex.UserRegex;


public class UserForm extends JDialog {
    private JLabel jlName;
    private JTextField tfName;
    private JLabel jlFirstname;
    private JTextField tfFirstname;
    private JLabel jlEmail;
    private JTextField tfEmail;
    private JLabel jlPassword;
    private JPasswordField pfPassword;
    private JButton btValid;
    private JButton btCancel;
    private JPanel jpMain;
    private JLabel jlPasswordVerif;
    private JPasswordField pfPasswordVerif;
    private JLabel jlInfo;

    public UserForm(JDialog parent) {
        super(parent);
        setTitle("Ajouter un compte utilisateur");
        setContentPane(jpMain);
        setMaximumSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //écouteur événement bt valider

        btValid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createUser();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createUser();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void createUser() throws SQLException {
        //récupérer le contenu des 5 champs de texte
        String name = tfName.getText();
        String firstname = tfFirstname.getText();
        String email = tfEmail.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String verifPassword = String.valueOf(pfPasswordVerif.getPassword());
        //test si les 5 champs sont bien remplis
        if (!name.isEmpty() && !firstname.isEmpty() && !email.isEmpty()) {
            //test si les passwords correspondent
            if (password.equals(verifPassword)) {
                // vérifie les regex
                if (!email.matches(UserRegex.USER_REGEX_MAIL)) {
                    JOptionPane.showMessageDialog(null,
                            "L'email n'est pas valide",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!password.matches(UserRegex.USER_REGEX_PASSWORD)) {
                    JOptionPane.showMessageDialog(null,
                            "Le mot de passe n'est pas valide",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                password = BCrypt.hashpw(password, BCrypt.gensalt());
                Utilisateur user = new Utilisateur(name, firstname, email, password);
                // Enregistrer l'utilisateur dans la base de données
                ManagerUtilisateur.create(user);
                //  Afficher un message pour indiquer que le compte a été ajouté
                JOptionPane.showMessageDialog(null,
                        "Compte ajouté en BDD avec succès",
                        "Valide",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Les mots de passe ne correspondent pas",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    "Veuillez remplir tous les champs du formulaire",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public void cancelUser() {
        //vider les champs
        tfName.setText("");
        tfFirstname.setText("");
        tfEmail.setText("");
        pfPassword.setText("");
        pfPasswordVerif.setText("");
        //afficher un message pour indiquer que les champs sont vidés
        JOptionPane.showMessageDialog(null,
                "Formulaire reset",
                "Reset",
                JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}

