package com.affines;

import com.utils.RealPoint;

public interface IAffine {
    RealPoint transform(RealPoint point, int k);
    RealPoint transform(RealPoint point);

}
