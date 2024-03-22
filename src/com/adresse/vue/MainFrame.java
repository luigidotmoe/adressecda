package com.adresse.vue;

import com.adresse.manager.ManagerUtilisateur;
import com.adresse.model.Utilisateur;
import com.sun.tools.javac.Main;
import jdk.jshell.execution.Util;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private JPanel ListeUtilisateurs;
    private JButton btnEditUser;
    private JButton btnDeleteUser;
    private JLabel nameAndFirstname;

    public MainFrame(){
        setLayout(new FlowLayout());
        setContentPane(ListeUtilisateurs);
        setTitle("Liste des utilisateurs");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Récupère tous les utilisateurs de la BDD
        ArrayList<Utilisateur> utilisateurs = ManagerUtilisateur.findAll();
        // Pour chaque utilisateur dans la liste
        for (Utilisateur utilisateur : utilisateurs) {
            // Crée un JLabel avec le nom et prénom de l'utilisateur
            nameAndFirstname = new JLabel(utilisateur.getName() + " " + utilisateur.getFirstname());
            System.out.println(nameAndFirstname.getText());
            // Ajoute le JLabel à la fenêtre
            add(nameAndFirstname);
            // Crée un bouton "Modifier"
            btnEditUser = new JButton("Modifier");
            // Ajoute le bouton à la fenêtre
            add(btnEditUser);
            // Crée un bouton "Supprimer"
            btnDeleteUser = new JButton("Supprimer");
            // Ajoute le bouton à la fenêtre
            add(btnDeleteUser);
        }
        pack();
        setVisible(true);
    }
}
