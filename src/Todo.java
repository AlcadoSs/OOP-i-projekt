import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//java.io.Serializable on objekti faili kirjutamiseks
public class Todo implements java.io.Serializable {

    //Isendiväljad String listiNimi to-do listi nime jaoks ja List<String> listTegevustest hoiab endas listi tegevustest
    private String listiNimi;
    private List<String> listTegevustest;

    //Konstruktor, mis annab isendiväljadele algsed väärtused
    public Todo() {
        this.listTegevustest = new ArrayList<>();
        this.listiNimi = "TODO:";
    }//Konstruktor

    //Setter, millega on võimalik muutujat listiNime muuta
    public void setListiNimi(String listiNimi) {
        this.listiNimi = listiNimi;
    }//Setter listiNimi

    public String getListiNimi() {
        return listiNimi;
    }//Getter listiNimi

    public List<String> getListTegevustest() {
        return listTegevustest;
    }//Getter ListTegevustest

    public void setListTegevustest(List<String> listTegevustest) {
        this.listTegevustest = listTegevustest;
    }//Setter ListTegevustest

    //Meetod, millega on võimalik listi uusi tegevusi lisada
    public void lisaTegevus(String tegevus){
        listTegevustest.add(tegevus);
    }//lisaTegevus

    //Meetod, millega on võimalik listist kindel tegevus eemaldada
    public String eemaldaTegevus(int i) {
        if (i < 1 || i > listTegevustest.size()) {
            return null;
        }else {
            i--;
            String element = listTegevustest.get(i);
            listTegevustest.remove(i);
            return element;
        }
    }//eemaldaTegevus

    //toString, mis annab to-do listile kuju
    @Override
    public String toString() {
        StringBuilder todo = new StringBuilder(listiNimi + "\n");
        for (int i = 0; i < this.listTegevustest.size(); i++) {
            todo.append(i + 1).append(". ").append(this.listTegevustest.get(i)).append("\n");
        }
        return todo.toString();
    }

    public static void main(String[] args) {
        Scanner kasutajaSisend = new Scanner(System.in);
        Todo todo = new Todo();

        boolean algus = true;
        while(algus) {
            System.out.println("Tere tulemast! Selle programmi abil on sul võiamoik kirjutada omale to-do listi.");
            System.out.println("Pärast programmi sulgemist loob programm kaks faili, üks on selleks, et soovi korral oleks võimalik oma to-do listi jätkata");
            System.out.println("ja teine on tavaline tekstifail selleks, et oma to-do listi hiljem vaadata.");
            System.out.println();
            System.out.println("Kas soovid avada olemasoleva to-do listi või luua uue?");
            System.out.println("Kirjutada vastavalt 'olemasolev'('o') või 'uus'('u').");
            String algusValik = kasutajaSisend.nextLine();
            if(algusValik.toLowerCase().equals("olemasolev") || algusValik.toLowerCase().equals("o")){
                System.out.println("Sisesta to-do listi nimi, mida soovite avada vormis nimi.txt.");
                String failinimi = kasutajaSisend.nextLine();
                //Olemasoleva faili avamine
                try {
                    FileInputStream fis = new FileInputStream(failinimi);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    todo = (Todo) ois.readObject();
                    ois.close();
                    algus = false;
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Mingi viga, proovi uuesti!");
                }
            } else if(algusValik.toLowerCase().equals("uus") || algusValik.toLowerCase().equals("u")) {
                String nimi;
                todo = new Todo();
                System.out.println("Sisesta, millist nime oma listile soovid:");
                nimi = kasutajaSisend.nextLine();
                todo.setListiNimi(nimi);
                algus = false;
            }
        }//esimene while tsükkel mainis

        int valik = 1;
        while(valik != 0) {

            System.out.println();
            System.out.println(todo);

            //Valikud
            System.out.println("VALIKUD:");
            System.out.println("1 - Lisa uus tegevus");
            System.out.println("2 - Eemalda kindel element");
            System.out.println("0 - Sulge");
            System.out.print("Sisesta oma valik: ");

            try {
                valik = kasutajaSisend.nextInt();
                kasutajaSisend.nextLine();

                switch (valik) {
                    case 1:
                        System.out.println("Sisestada, mida soovid oma todo-listi lisada: ");
                        String tekst = kasutajaSisend.nextLine();
                        todo.lisaTegevus(tekst);
                        break;
                    case 2:
                        System.out.print("Sisesta, mitmenda rea soovid eemaldada: ");
                        int i = kasutajaSisend.nextInt();
                        String eemaldatud = todo.eemaldaTegevus(i);

                        if (eemaldatud != null) {
                            System.out.println();
                            System.out.println("Eemaldatud: " + eemaldatud);
                        } else {
                            System.out.println();
                            System.out.println("Sellise indeksiga rida ei leitud " + i + ".");
                        }
                        break;

                    case 0:
                        System.out.println("Aitäh, et kasutasid meie programmi. Tehtud to-do listi näete failist nimi-loetav.txt.");
                        break;

                    default:
                        System.out.println();
                        System.out.println(valik + ", sellist valikut ei ole, proovi uuesti.");
                        break;
                }
            }catch (java.util.InputMismatchException ime){
                System.out.println("Sisesta number palun.");
                kasutajaSisend.nextLine();
            }
        }//teine while tsükkel mainis

        try {
            //Loob to-do listiga sama nimega faili, kus hoiab infot objekti kohta
            FileOutputStream fos = new FileOutputStream(todo.getListiNimi() + ".txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(todo);
            oos.close();

            //Loob tavalise loetava teksti faili to-do listist
            FileWriter writer = new FileWriter(todo.getListiNimi() + "-loetav.txt");
            for(String rida: todo.getListTegevustest()) {
                writer.write(rida + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//main
}//To-do
