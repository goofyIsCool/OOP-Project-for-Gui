package Program;

import Container.*;

public class IrresponsibleSenderWithDangerousGoods extends Exception{
    private static int timeBeforeDeletion = 10; //2days

    public IrresponsibleSenderWithDangerousGoods(String msg){
        super(msg);
    }

    public static void checkIf(Container c, int time) throws IrresponsibleSenderWithDangerousGoods {
        int timeOfContainerInWarehouse = ThreadTimer.getSeconds() - time;

        if (timeOfContainerInWarehouse - timeBeforeDeletion > 5*5 && c instanceof ExplosiveCargo)
            throw new IrresponsibleSenderWithDangerousGoods(ConsoleColors.RED + "Warning! ExplosiveContainer (id=" + c.getId() + ") in the warehouse will soon be disposed of!" + ConsoleColors.RESET);
        else if (timeOfContainerInWarehouse - time - timeBeforeDeletion > 5*10 && c instanceof ToxicCargo)
            throw new IrresponsibleSenderWithDangerousGoods(ConsoleColors.RED + "Warning! ToxicCargo Liquid (id=" + c.getId() + ") in the warehouse will soon be disposed of!" + ConsoleColors.RESET);
        else if (timeOfContainerInWarehouse - time - timeBeforeDeletion> 5*14 && c instanceof ToxicCargo) //Change
            throw new IrresponsibleSenderWithDangerousGoods(ConsoleColors.RED + "Warning! ToxicCargo Powdery (id=" + c.getId() + ") in the warehouse will soon be disposed of!" + ConsoleColors.RESET);
    }
}
