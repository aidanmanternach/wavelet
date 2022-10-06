import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> strArrL = new ArrayList<>();

    public String handleRequest(URI url) {
        ArrayList<String> printArr = new ArrayList<>();
        if (url.getPath().equals("/")) {
            for(int i = 0; i < strArrL.size(); i++) {
                printArr.add(strArrL.get(i));
            }
            return printArr.toString();
        } else if (url.getPath().equals("/search")) {
            System.out.println("Path: " + url.getPath());
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                for(int i = 0; i < strArrL.size(); i++) {
                    if(strArrL.get(i).contains(parameters[1])) {
                        printArr.add(strArrL.get(i));
                    }
                }
                return printArr.toString();
            }
            return "Not valid";
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    strArrL.add((parameters[1]));
                    return String.format("%s was added", parameters[1]);
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}