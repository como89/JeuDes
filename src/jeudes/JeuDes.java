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
        int [] tabNombreDes = new int[6];
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
                    jouerPartie(joueur1,joueur2,tabDes,tabBoolean,random,tabNombreDes);
            }
        }
    }
    private static void jouerPartie(String joueur1,String joueur2,int[]tabDes,boolean[]tabBoolean,Random random,int [] tabNombreDes) throws IOException
    {
        int total = 0;
        genererDes(tabDes,random);
        reinitialiserTabNombreDes(tabNombreDes);
        calculerNombreDes(tabNombreDes,tabDes);
        total = calculerPoints(tabNombreDes);
        afficherDes(tabDes,total);
        String choix = lireChoixPartie();
        //135
        verifierChoix(tabBoolean,choix);
        if(verifierRebrassage(tabBoolean))
        {
        rebrasserDes(tabBoolean,tabDes,random);
        reinitialiserTabNombreDes(tabNombreDes);
        calculerNombreDes(tabNombreDes,tabDes);
        total = calculerPoints(tabNombreDes);
        afficherDes(tabDes,total);
        }
    }
    
    private static void reinitialiserTabNombreDes(int[] tabNombreDes)
    {
        //Réinitialise notre tableau de dés à 0.
        for(int i = 0; i < tabNombreDes.length; i++)
        {
            tabNombreDes[i] = 0;
        }
    }
    
    private static int calculerPoints(int[] tabNombreDes)
    {
        int total = 0;
        int totalBrelan = 0;
        int totalFull = 0;
        int totalSuite = 0;
        int totalAutres = 0;
        
        totalBrelan = calculerBrelan(tabNombreDes);
        totalFull = calculerFull(totalBrelan,tabNombreDes);
        totalSuite = calculerSuite(tabNombreDes);
        totalAutres = calculerAutres(totalFull,totalSuite,tabNombreDes);
        
        total += totalBrelan + totalFull + totalSuite + totalAutres;
        return total;
    }
    
    private static int calculerAutres(int totalFull, int totalSuite, int[] tabNombreDes)
    {
        int totalAutres = 0;
        
        if( totalFull == 0 && totalSuite == 0)
        {
            if(tabNombreDes[0] <= 2 && tabNombreDes[0] > 0)
            {
                totalAutres += 100 * tabNombreDes[0];
            }
            if ( tabNombreDes[4] <= 2 && tabNombreDes[4] > 0)
            {
                totalAutres += 50 * tabNombreDes[4];
            }
        }
        return totalAutres;
    }
    
    private static int calculerSuite(int [] tabNombreDes)
    {
        int totalSuite = 0;
        
        if(tabNombreDes[0] == 1 && tabNombreDes[1] == 1 
                && tabNombreDes[2] == 1 && tabNombreDes[3] == 1
                && tabNombreDes[4] == 1 || tabNombreDes[1] == 1 
                && tabNombreDes[2] == 1 && tabNombreDes[3] == 1 
                && tabNombreDes[4] == 1 && tabNombreDes[5] == 1)
        {
            totalSuite += 1500;
        }
        return totalSuite;
    }
    
    private static int calculerFull(int totalBrelan,int [] tabNombreDes)
    {
        int totalFull = 0;
        
        if(totalBrelan > 0)
        {
          for(int i = 0; i < tabNombreDes.length; i++)
          {
              if(tabNombreDes[i] == 2)
              {
                  totalFull += (i + 1) * 50;
              }
          }
        }
        return totalFull;
    }
    
    private static int calculerBrelan(int [] tabNombreDes)
    {
        int totalBrelan = 0;
        for(int i = 0; i < tabNombreDes.length; i++)
        {
            if(tabNombreDes[i] >= 3)
            {
                totalBrelan += (i + 1) * 100;
            }
        }
        return totalBrelan;
    }
    
    private static boolean verifierRebrassage(boolean[] tabBoolean)
    {
        for(int i = 0; i < tabBoolean.length;i++)
        {
            if(tabBoolean[i])
            {
                return true;
            }
        }
            return false;
    }
    
    private static void calculerNombreDes(int[] tabNombreDes,int [] tabDes)
    {
        for(int i = 0; i < tabDes.length; i++)
        {
            for(int j = 0; j < tabNombreDes.length; j++)
            {
                if(tabDes[i] == j + 1)
                {
                    tabNombreDes[j]++;
                }
            }
        }
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
        System.out.println("Veuillez choisir le ou les dés à rebrasser : (1 à 5) ");
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
    
    private static void afficherDes(int[] tabDes,int total)
    {
        for(int i = 0; i < tabDes.length; i++)
        {
            System.out.print(tabDes[i] + " ");
        }
        System.out.println("Points : " + total);
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
