package Login.Util;

import Login.Enums.TipoDeInvalidacao;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Period;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class Geradores {

    String ramdomBaseString;
    public Geradores() {
        UUID uuid = UUID.randomUUID();
        ramdomBaseString = uuid.toString();
    }

    public String gerarLoginRandomico(){
        return ramdomBaseString.substring(0,15)+"@dbccompany.com.br";
    }

    public String gerarLoginRandomico(TipoDeInvalidacao tipoDeInvalidacao){
        if(tipoDeInvalidacao == TipoDeInvalidacao.EMAILSEMDOMINIO){
            return ramdomBaseString.substring(0,15);
        }
        else if(tipoDeInvalidacao == TipoDeInvalidacao.NULO){
            return null;
        }
        else if(tipoDeInvalidacao == TipoDeInvalidacao.SEMPONTO){
            return ramdomBaseString.substring(0,15)+"@dbccompanycombr";
        }
        else if(tipoDeInvalidacao == TipoDeInvalidacao.SOMENTESIMBOLOS){
            return "&%!*@¨#@***&@.@#!.(*#";
        }
        else if(tipoDeInvalidacao == TipoDeInvalidacao.SOMENTENUMEROS){
            return "21584685@235468.654.32";
        }
        else if(tipoDeInvalidacao == TipoDeInvalidacao.EMBRANCO){
            return "";
        }
        return null;
    }



    public String gerarSenhaRandomica(){
        return ramdomBaseString.substring(0,9);
    }

    public String gerarSenhaRandomica(TipoDeInvalidacao tipoDeInvalidacao){
        if(tipoDeInvalidacao == TipoDeInvalidacao.ABAIXO_TAMANHO_MINIMO){
            return ramdomBaseString.substring(0,7);
        }
        else if(tipoDeInvalidacao == TipoDeInvalidacao.NULO){
            return null;
        }
        else if(tipoDeInvalidacao == TipoDeInvalidacao.ACIMA_TAMANHO_MAXIMO){
            String substring1 = ramdomBaseString.substring(0, 36);
            String substring2 = ramdomBaseString.substring(0, 36);
            return substring1+substring2;
        }
        else if(tipoDeInvalidacao == TipoDeInvalidacao.SOMENTESIMBOLOS){
            return "&%!*@¨#@**";
        }
        else if(tipoDeInvalidacao == TipoDeInvalidacao.SOMENTENUMEROS){
            return "21584685";
        }
        else if(tipoDeInvalidacao == TipoDeInvalidacao.EMBRANCO){
            return "";
        }
        return gerarSenhaRandomica();
    }


    public static LocalDate randomBirthday() {
        return LocalDate.now().minus(Period.ofDays((new Random().nextInt(365 * 70))));
    }
    public static LocalDate randomInvalidBirthday() {
        return LocalDate.now().plus(Period.ofDays((new Random().nextInt(365 * 70))));
    }

    public static String gerarNumeroDeTelefone(){
        Random gerador = new Random();
        double phone = gerador.nextDouble();
        return Double.toString(phone).substring(2,13);
    }


    public static String gerarCEP() {
        String cepsValidosPath = "src/test/java/MassaDeDados/cepsValidos";
        Scanner scanner = null;
        try {
            scanner = new Scanner( new File(cepsValidosPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String cep = "";
        for(int i = 0; i < new Random().nextInt(20000); i++){
            cep = scanner.nextLine();
        }
        return cep;
    }

    public static String gerarUF(){
        String[] estados = {"AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MT","MS","MG","PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
        return estados[new Random().nextInt(27)];
    }


    private static int randomiza(int n) {
        int ranNum = (int) (Math.random() * n);
        return ranNum;
    }

    private static int mod(int dividendo, int divisor) {
        return (int) Math.round(dividendo - (Math.floor(dividendo / divisor) * divisor));
    }

    public static String cpf(boolean comPontos) {
        int n = 9;
        int n1 = randomiza(n);
        int n2 = randomiza(n);
        int n3 = randomiza(n);
        int n4 = randomiza(n);
        int n5 = randomiza(n);
        int n6 = randomiza(n);
        int n7 = randomiza(n);
        int n8 = randomiza(n);
        int n9 = randomiza(n);
        int d1 = n9 * 2 + n8 * 3 + n7 * 4 + n6 * 5 + n5 * 6 + n4 * 7 + n3 * 8 + n2 * 9 + n1 * 10;

        d1 = 11 - (mod(d1, 11));

        if (d1 >= 10)
            d1 = 0;

        int d2 = d1 * 2 + n9 * 3 + n8 * 4 + n7 * 5 + n6 * 6 + n5 * 7 + n4 * 8 + n3 * 9 + n2 * 10 + n1 * 11;

        d2 = 11 - (mod(d2, 11));

        String retorno = null;

        if (d2 >= 10)
            d2 = 0;
        retorno = "";

        if (comPontos)
            retorno = "" + n1 + n2 + n3 + '.' + n4 + n5 + n6 + '.' + n7 + n8 + n9 + '-' + d1 + d2;
        else
            retorno = "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + d1 + d2;

        return retorno;
    }

    private String removeCaracteresEspeciais(String doc) {
        if (doc.contains(".")) {
            doc = doc.replace(".", "");
        }
        if (doc.contains("-")) {
            doc = doc.replace("-", "");
        }
        if (doc.contains("/")) {
            doc = doc.replace("/", "");
        }
        return doc;
    }


}
