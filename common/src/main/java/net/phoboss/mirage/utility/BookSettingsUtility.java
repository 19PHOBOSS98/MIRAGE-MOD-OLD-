package net.phoboss.mirage.utility;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.Map;

public interface BookSettingsUtility {
    static NbtList readPages(ItemStack bookStack){
        if (!bookStack.isEmpty() && bookStack.hasNbt()) {
            NbtCompound bookNbt = bookStack.getNbt();
            if(bookNbt.contains("pages")) {
                return bookNbt.getList("pages", 8).copy();
            }
        }
        return new NbtList();
    }
    static JsonObject parsePages(NbtList pagesNbt, Book bookSettings) throws Exception{
        if(pagesNbt.size()<1){
            throw new Exception("The book is empty: ");
        }
        String pagesStr = "{";
        for(int i=0; i<pagesNbt.size(); ++i) {
            pagesStr = pagesStr + pagesNbt.getString(i);
        }
        pagesStr = pagesStr + "}";

        JsonObject settingsJSON;
        try {
            settingsJSON  = JsonParser.parseString(pagesStr).getAsJsonObject();
        }catch (Exception e){
            throw new Exception("Might need to recheck your book: "+e.getLocalizedMessage(),e);
        }
        return validateNewSettings(settingsJSON,bookSettings);
    }


    static JsonObject validateNewSettings(JsonObject settingsJSON,Book book) throws Exception{

        /*
        MirageProjectorBook testBook = new MirageProjectorBook();
        testBook.getFrames().put(1,new Frame());
        JsonObject testJSON = new Gson().toJsonTree(testBook).getAsJsonObject();
*/

        JsonObject bookJSON = new Gson().toJsonTree(book).getAsJsonObject();

        for (Map.Entry<String, JsonElement> setting : settingsJSON.entrySet()) {
            String settingName = setting.getKey();
            if(!bookJSON.has(settingName)) {
                throw new Exception("Unrecognized Setting: " + settingName);
            }
            JsonElement oldValue = bookJSON.get(settingName);
            JsonElement newValue = setting.getValue();
            if(oldValue.getClass() != newValue.getClass()){
                throw new Exception("Invalid Entry: " + settingName + ":" + newValue);
            }
            bookJSON.add(settingName,newValue);
        }
        return bookJSON;
        //book = new Gson().fromJson(bookJSON, MirageProjectorBook.class);
    }

    default void executeBookProtocol(ItemStack bookStack,
                                             BlockEntity blockEntity,
                                             Book bookSettings) throws Exception{
        NbtList pagesNbt;
        try {
            pagesNbt = readPages(bookStack);
        }catch(Exception e){
            //ErrorResponse.onError(world,pos,player,"can't find pages...");
            throw new Exception("can't find pages...",e);
        }

        if(pagesNbt.isEmpty()){
            throw new Exception("The book is empty...");
        }

        try {
            JsonObject newSettings = parsePages(pagesNbt, bookSettings);
            implementBookSettings(blockEntity,newSettings);
        }catch(Exception e){
            throw new Exception(e.getMessage(),e);
        }
    }


    default void implementBookSettings(BlockEntity blockEntity, JsonObject newSettings) throws Exception{
    }

}
