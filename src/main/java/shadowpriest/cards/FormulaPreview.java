package shadowpriest.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

/** Keep the printed formula, with a combat-only parenthetical result. */
final class FormulaPreview {
    static void update(AbstractCard card, com.megacrit.cardcrawl.monsters.AbstractMonster target) {
        String result=null;
        boolean handled=true;
        if(inCombat())switch(card.cardID){
            case MindBlast.ID: result=target==null?null:card.damage+" damage";break;
            case Insight.ID: result=target==null?null:shadowpriest.powers.ShadowDotPower.count(target)+" cards";break;
            case MindSoothe.ID: result=target==null?null:(shadowpriest.powers.ShadowDotPower.count(target)+(card.upgraded?2:1))+" less damage per hit";break;
            case MaladyOfTheMind.ID: result=target==null?null:(shadowpriest.powers.MadnessPower.stacks(target)*(card.magicNumber-1))+" Madness to apply";break;
            case MadnessOverwhelming.ID: result=((MadnessOverwhelming)card).application()+" Madness";break;
            case VoidTendrils.ID:
                int enemies=0;for(com.megacrit.cardcrawl.monsters.AbstractMonster m:AbstractDungeon.getMonsters().monsters)if(VoidBolt.alive(m))enemies++;
                result=(enemies*card.block)+" total Block";break;
            case ShadowWeaving.ID:
                int dots=0;for(com.megacrit.cardcrawl.monsters.AbstractMonster m:AbstractDungeon.getMonsters().monsters)if(VoidBolt.alive(m))dots+=shadowpriest.powers.ShadowDotPower.count(m);
                PowerWordShield calculation=new PowerWordShield();calculation.baseBlock=dots*card.magicNumber;calculation.applyPowers();
                result=(dots==0?0:calculation.block)+" Block at current DoTs";break;
            default:handled=false;
        }
        else handled=card instanceof MindBlast || card instanceof Insight || card instanceof MindSoothe || card instanceof MaladyOfTheMind || card instanceof MadnessOverwhelming || card instanceof VoidTendrils || card instanceof ShadowWeaving;
        if(handled)set(card,result);
    }
    static boolean inCombat() {
        return AbstractDungeon.player != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null
                && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }
    static void set(AbstractCard card, String result) {
        CardStrings strings = CardCrawlGame.languagePack.getCardStrings(card.cardID);
        String base = card.upgraded && strings.UPGRADE_DESCRIPTION != null
                && !strings.UPGRADE_DESCRIPTION.isEmpty() ? strings.UPGRADE_DESCRIPTION : strings.DESCRIPTION;
        String text = base + (result == null || !inCombat() ? "" : " NL (" + result + ")");
        if (!text.equals(card.rawDescription)) { card.rawDescription = text; card.initializeDescription(); }
    }
}
