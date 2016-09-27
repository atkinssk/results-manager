package uk.org.windswept.resultsmanager.ftp.vfs;

import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

/**
 * Created by 802998369 on 27/09/2016.
 */
public class VfsFile
{
    enum Type
    {
        FILE,
        DIRECTORY,
        MISSING;
    }

    private String name;
    private String content; // TODO should probably be byte array
    private long lastModified;
    private Set<VfsFile> children = newHashSet();
    private VfsFile parent;

    private VfsFile()
    {
        this("", Type.DIRECTORY);
    }

    private VfsFile(String name, Type type)
    {
        this.name = name;
        this.type = type;
        lastModified = System.currentTimeMillis();
    }

    public static VfsFile newRootInstance()
    {
        return new VfsFile();
    }

    public static VfsFile newFileInstance(String name)
    {
        return new VfsFile(name, Type.FILE);
    }

    public static VfsFile newDirectoryInstance(String name)
    {
        return new VfsFile(name, Type.DIRECTORY);
    }

    public static VfsFile newMissingInstance(String name, VfsFile toBeParent)
    {
        return new VfsFile(name, Type.MISSING).setParent(toBeParent);
    }


    public VfsFile addChild(VfsFile child)
    {
        children.add(child);
        child.setParent(this);
        return this;
    }

    public VfsFile addTo(VfsFile toBeParent)
    {
        toBeParent.addChild(this);
        return this;
    }

    public VfsFile setContent(String content)
    {
        this.content = content;
        // If we are havign content added then we must be a file
        type = Type.FILE;
        lastModified = System.currentTimeMillis();
        return this;
    }

    private VfsFile setParent(VfsFile parent)
    {
        this.parent = parent;
        return this;
    }
    public String getName()
    {
        return name;
    }

    public String getAbsolutePath()
    {
        String absolutePath;
        if (parent == null)
        {
            absolutePath = getName();
        }
        else
        {
            absolutePath = parent.getAbsolutePath() + getName();
        }

        if (isDir())
        {
            absolutePath =  absolutePath + "/" ;
        }

        return absolutePath;
    }

    public Type getType()
    {
        return type;
    }

    public boolean isDir()
    {
        return Type.DIRECTORY.equals(getType());
    }

    public boolean isFile()
    {
        return Type.FILE.equals(getType());
    }

    public boolean isExists()
    {
        return !Type.MISSING.equals(getType());
    }

    public String getContent()
    {
        return content;
    }

    public long getSize()
    {
        return content.length();
    }

    public long getLastModified()
    {
        return lastModified;
    }

    public void setLastModified(long lastModified)
    {
        this.lastModified = lastModified;
    }


    private Type type;

    public Set<VfsFile> getChildren()
    {
        // TODO this should probably return an unmodifiyable set
        return children;
    }

    public VfsFile getParent()
    {
        return parent;
    }

    public VfsFile getSubDirectory(String dir)
    {
        for (VfsFile file : getChildren())
        {
            if(file.isDir() && file.getName().equals(dir))
            {
                return file;
            }
        }
        return null;
    }

    public VfsFile getChildFile(String name)
    {
        for (VfsFile file : getChildren())
        {
            if(file.isFile() && file.getName().equals(name))
            {
                return file;
            }
        }
        return null;
    }
}
