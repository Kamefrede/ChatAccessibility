function injectForEachInsn(method, opcode, callback) {
    var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');

    var target = ASM.findFirstInstruction(method,
        opcode);

    while (target !== null) {
        var index = method.instructions.indexOf(target);
        var indexShift = callback(target, index);

        var newIndex = method.instructions.indexOf(target);
        if (newIndex !== -1)
            index = newIndex;
        else if (typeof indexShift === 'number')
            index += indexShift;

        target = ASM.findFirstInstructionAfter(method,
            opcode,
            index + 1);
    }

    return method;
}

function initializeCoreMod() {
    return {
        'health-bar': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.gui.overlay.PlayerTabOverlayGui',
                'methodName': 'func_200262_a', // getDisplayName
                'methodDesc': '(Lnet/minecraft/client/network/play/NetworkPlayerInfo;)Lnet/minecraft/util/text/ITextComponent;'
            },
            'transformer': function(method) {
                var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');
                var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
                var InsnList = Java.type('org.objectweb.asm.tree.InsnList');

                return injectForEachInsn(method, Opcodes.ARETURN, function (target) {
                    var newInstructions = new InsnList();

                    newInstructions.add(ASM.buildMethodCall(
                        "xyz/kamefrede/chataccessibility/ChatAccessibility",
                        "setPlayerlistColor",
                        "(Lnet/minecraft/util/text/ITextComponent;)Lnet/minecraft/util/text/ITextComponent;",
                        ASM.MethodType.STATIC
                    ));
                    method.instructions.insertBefore(target, newInstructions);
                })
            }
        }
    }
}