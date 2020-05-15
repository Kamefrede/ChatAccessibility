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
                'class': 'net.minecraft.client.gui.NewChatGui',
                'methodName': 'func_146234_a', // printChatMessageWithOptionalDeletion
                'methodDesc': '(Lnet/minecraft/util/text/ITextComponent;I)V'
            },
            'transformer': function(method) {
                var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');
                var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
                var InsnList = Java.type('org.objectweb.asm.tree.InsnList');

                var newInstructions = new InsnList();

                newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
                newInstructions.add(ASM.buildMethodCall(
                    "xyz/kamefrede/chataccessibility/ChatAccessibility",
                    "setChatColor",
                    "(Lnet/minecraft/util/text/ITextComponent;)Lnet/minecraft/util/text/ITextComponent;",
                    ASM.MethodType.STATIC
                ));
                newInstructions.add(new VarInsnNode(Opcodes.ASTORE, 1));
                method.instructions.insertBefore(method.instructions.getFirst(), newInstructions);
                return method;
            }
        }
    }
}