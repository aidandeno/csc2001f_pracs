
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Command line program interface.
 *
 * @author Stephan Jamieson
 * @version 4/3/2015
 */
public class TreeUI
{

    private AVLTree target;

    private Map<String, Command> commands;

    public TreeUI()
    {
        commands = new HashMap<String, Command>();
        //commands.put("new", new New());
        //commands.put("insert", new Insert());
        commands.put("contains", new Contains());
        //commands.put("print", new Print());
        //commands.put("write", new Write());
        commands.put("help", new Help());
        commands.put("quit", new Quit());
        commands.put("new", new New());
        commands.put("insert", new Insert());
        commands.put("print", new Print());
        commands.put("write", new Write());
        target = new AVLTree();
    }

    public void run()
    {
        Scanner input = new Scanner(System.in);
        commands.get("help").execute("");

        while (true)
        {
            System.out.print(">");
            Scanner line = new Scanner(input.nextLine());

            String keyword = (line.hasNext() ? line.next().trim().toLowerCase() : "");
            String argument = (line.hasNext() ? line.next() : "");

            if (commands.containsKey(keyword))
            {
                commands.get(keyword).execute(argument);
            }
            else
            {
                System.out.println("Sorry, that command is not recognised. Type 'help' for assistance.");
            }
        }
    }

    public static void main(String[] args)
    {
        (new TreeUI()).run();
    }

    private abstract class Command
    {
        public abstract String help();

        public abstract void execute(String argument) throws IllegalArgumentException;

    }

    private class Contains extends Command
    {
        @Override
        public String help()
        {
            return "contains <value>";
        }

        @Override
        public void execute(String argument) throws IllegalArgumentException
        {
            try
            {
                String response = target.contains(argument) ? "Yes" : "No";
                System.out.println(response);
            }
            catch (IllegalArgumentException numFormE)
            {
                throw new IllegalArgumentException("Insert " + argument + " : argument not an integer.");
            }
        }
    }

    private class Help extends Command
    {
        @Override
        public String help()
        {
            return "help";
        }

        @Override
        public void execute(String argument) throws IllegalArgumentException
        {

            Iterator<String> keywordIter = commands.keySet().iterator();

            if (keywordIter.hasNext())
            {
                System.out.print("Commands: " + commands.get(keywordIter.next()).help());
                while (keywordIter.hasNext())
                {
                    System.out.print(", " + commands.get(keywordIter.next()).help());
                }
                System.out.println(".");
            }
            else
            {
                System.out.println("No commands installed.");
            }
        }
    }

    private class Quit extends Command
    {
        @Override
        public String help()
        {
            return "quit";
        }

        @Override
        public void execute(String argument)
        {
            System.exit(0);
        }
    }

    private class New extends Command
    {
        @Override
        public String help()
        {
            return "new";
        }

        @Override
        public void execute(String argument)
        {
            target = new AVLTree();
        }
    }

    private class Insert extends Command
    {
        @Override
        public String help()
        {
            return "insert <value>";
        }

        @Override
        public void execute(String argument) throws IllegalArgumentException
        {
            try
            {
                target.insert(argument);
            }
            catch (NumberFormatException e)
            {
                throw new IllegalArgumentException("Contains " + argument + " : argument not an integer.");
            }
        }
    }

    private class Print extends Command
    {
        @Override
        public String help()
        {
            return "print";
        }

        @Override
        public void execute(String argument)
        {
            target.print(System.out);
        }
    }

    private class Write extends Command
    {
        @Override
        public String help()
        {
            return "write <file name>";
        }

        @Override
        public void execute(String argument) throws IllegalArgumentException
        {
            try
            {
                target.print(new PrintStream(new File(argument)));
            }
            catch (FileNotFoundException ex)
            {
                //do nothing
            }
        }
    }
}