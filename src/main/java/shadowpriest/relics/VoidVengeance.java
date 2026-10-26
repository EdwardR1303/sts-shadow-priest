package shadowpriest.relics;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import shadowpriest.cards.VoidBolt;
public class VoidVengeance extends PriestRelic {
    public static final String ID="shadowpriest:VoidVengeance";
    public VoidVengeance(){super(ID,RelicTier.BOSS,"shadowpriest/images/cards/icons/void_bolt.jpg");}
    @Override public AbstractRelic makeCopy(){return new VoidVengeance();}
    @SpirePatch(clz=CardGroup.class,method="initializeDeck")
    public static class Convert {
        @SpirePostfixPatch public static void after(CardGroup __instance){
            if(AbstractDungeon.player==null||!AbstractDungeon.player.hasRelic(ID))return;
            for(int i=0;i<__instance.group.size();i++)if(__instance.group.get(i).type==AbstractCard.CardType.CURSE)
                __instance.group.set(i,new VoidBolt());
        }
    }
}
