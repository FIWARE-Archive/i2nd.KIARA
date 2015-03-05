package org.fiware.kiara.typecode.impl.data;

import org.fiware.kiara.typecode.TypeKind;
import org.fiware.kiara.typecode.data.ContainerTypeDescriptor;
import org.fiware.kiara.typecode.data.DataTypeDescriptor;

public class ContainerTypeDescriptorImpl extends DataTypeDescriptorImpl implements ContainerTypeDescriptor {

    protected DataTypeDescriptor m_contentType = null;
    
    private int m_maximumSize;
    
    public ContainerTypeDescriptorImpl(TypeKind kind) {
        super(kind);
        this.m_maximumSize = 100;
    }
    
    /*public ContainerTypeDescriptorImpl(TypeKind kind, String name, DataTypeDescriptorImpl contentType) {
        super(kind);
        this.m_maximumSize = 100;
    }*/
    
    @Override
    public boolean isContainer() {
        return true;
    }
    
    @Override
    public void setMaxSize(int size) {
        this.m_maximumSize = size;
    }
    
    @Override
    public int getMaxSize() {
        return this.m_maximumSize;
    }
    
    

}
