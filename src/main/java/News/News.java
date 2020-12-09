package News;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class News {

    public static String getNews(String message, Model model) throws IOException {

        URL url = new URL("https://yandex.ru/news/");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }
        return result;

    }
}
