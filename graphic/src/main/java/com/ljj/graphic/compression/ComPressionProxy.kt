package com.ljj.graphic.compression

import com.common.lib.kit.util.BaseProxy

object ComPressionProxy {
    fun create(): CompressionInterface {
        return BaseProxy(LubanManager).create()
    }
}