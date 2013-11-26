/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 *
 * @author Nicholas
 */
public class JeuDes
{

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException
    {
        int choix = 0;
        String joueur1 = "Joueur 1";
        String joueur2 = "Joueur 2";
        int [] tabDes = new int[5];
        Random random = new Random();
        boolean [] tabBoolean = new boolean[tabDes.length];
        
        while(choix != 4)
        {
            afficherMenuPrincipal();
            choix = lireChoix();
            switch(choix)
            {
                case 1 :
                    afficherRegle();
                    break;
                case 2 :
                    joueur1 = changerNomJoueur();
                    joueur2 = changerNomJoueur();
                    break;
                case 3 :
                    jouerPartie(joueur1,joueur2,tabDes,tabBoolean,random);
            }
        }
    }
    private static void jouerPartie(String joueur1,String joueur2,int[]tabDes,boolean[]tabBoolean,Random random) throws IOException
    {
        genererDes(tabDes,random);
        afficherDes(tabDes);
        String choix = lireChoixPartie();
        //135
        verifierChoix(tabBoolean,choix);
        rebrasserDes(tabBoolean,tabDes,random);
        afficherDes(tabDes);
    }
    
    private static void rebrasserDes(boolean[] tabBoolean,int[] tabDes, Random random)
    {
        for(int i = 0; i < tabDes.length;i++)
        {
            if(tabBoolean[i])
            {
                tabDes[i] = random.nextInt(5)+1; //1 à 6
                tabBoolean[i] = false;
            }
        }
    }
    
    private static void verifierChoix(boolean[] tabBoolean,String choix)
    {
        choix = choix.trim();
        String[] tabChoix = choix.split(""); // [""] [1] [3] [5]
        for (String choixS : tabChoix)
        {
            if(!choixS.isEmpty())
            {
                for(int j = 0 ; j < tabBoolean.length; j++)
                {
                    if(Integer.parseInt(choixS) - 1 == j)
                    {
                        tabBoolean[j] = true;
                    }
                }
            }
        }
    }
    
    private static String lireChoixPartie() throws IOException
    {
        System.out.println("Veuillez choisir le ou les dés à rebrasser : (1 à 5)");
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        return r.readLine();
    }
    
    private static void genererDes(int[] tabDes,Random random)
    {
        for(int i = 0; i < tabDes.length; i++)
        {
            tabDes[i] = random.nextInt(5)+1;
        }
    }
    
    private static void afficherDes(int[] tabDes)
    {
        for(int i = 0; i < tabDes.length; i++)
        {
            System.out.print(tabDes[i] + " ");
        }
        System.out.println();
    }
    
    private static void afficherMenuPrincipal()
    {
        System.out.println("{------Menu-----]");
        System.out.println("1- Règles");
        System.out.println("2- Changer le nom des joueurs");
        System.out.println("3- Jouer");
        System.out.println("4- Quitter");
    }
    
    private static int lireChoix() throws IOException 
    {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        return Integer.parseInt(r.readLine());
    }
    
    private static void afficherRegle()
    {
        System.out.println("Afficher Règles");
    }
    
    private static String changerNomJoueur() throws IOException
    {
        System.out.print("Veuillez écrire le nom du joueur : ");
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        return r.readLine();
    }
}
