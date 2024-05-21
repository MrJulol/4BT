package org.example.memorygame;

import java.util.ArrayList;
import java.util.List;

public class ImageDatabase {
    private static ImageDatabase instance;
    private List<String> images;
    private ImageDatabase() {
        this.images = new ArrayList<>();
        images.add("./src/main/java/org/example/memorygame/images/armadillo.jpg");
        images.add("./src/main/java/org/example/memorygame/images/fennec.jpeg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/fox.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/idk_viech.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/snep.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/zebra.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/fennec2.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/panda.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/monke.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/frog.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/zebra.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/zebra.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/zebra.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/zebra.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/zebra.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/zebra.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/zebra.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/zebra.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/zebra.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/zebra.jpg");
        images.add("/Users/riven/dev/school/4BT/Info/Java/Memory-Game/src/main/java/org/example/memorygame/images/zebra.jpg");


    }
    public static ImageDatabase getInstance() {
        if (instance == null) {
            instance = new ImageDatabase();
        }
        return instance;
    }
    public List<String> getImages(int amount) {
        List<String> images = this.images.subList(0, amount/2);
        if(amount % 2 == 0){
            List<String> subImages = this.images.subList(0, amount/2);
            images.addAll(subImages);

        }else{
            List<String> temp = this.images.subList(0, amount/2)
                    .stream()
                    .filter((s -> !s.equals(this.images.subList(0, amount/2).getLast()))).toList();
            images.addAll(temp);
        }
        return images;
    }
}
