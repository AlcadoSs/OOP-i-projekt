import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Todo implements java.io.Serializable {

    //Isendiväli String listiNimi to-do listi nime jaoks, List<String> listTegevustest hoiab endas listi tegevustest
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

        String nimi;
        System.out.println("Sisesta, millist nime oma listile soovid:");
        nimi = kasutajaSisend.nextLine();
        todo.setListiNimi(nimi);

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
                        System.out.println();
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
        }
    }//main
}//To-do
