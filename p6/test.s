	.data
	.align 2
_g:	.space 4	# Global var g
	.data
	.align 2
_g1:	.space 4	# Global var g1
	.text
_f1:	
	sw    $ra, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	sw    $fp, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	addu  $fp, $sp, 16		#set the FP for this function
	subu  $sp, $sp, 12		#push space for locals
	lw    $t0, 0($fp)	#get value of l4
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 2		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	mult  $t0, $t1		#operation *
	mflo  $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	la    $t0, -16($fp)	#get address of l16
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t0, 0($t1)	#ASSIGN
	la    $t0, -16($fp)	#get address of l16
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 0($t0)	#put value into T1
	move  $a0, $t1		#put value to A0
	li    $v0, 1
	syscall
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	blt   $t0, $t1, .L1		#operation <
	li    $t0, 0		#not less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L2
.L1:
	li    $t0, 1		#less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L2:
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	beq   $t0, 0, .L0
	li    $t0, 0		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	beq   $t0, 1, .L4
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L5
.L4:
	li    $t0, 0		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L5:
	j     .L3
.L0:
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	blt   $t0, $t1, .L6		#operation <
	li    $t0, 0		#not less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L7
.L6:
	li    $t0, 1		#less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L7:
.L3:
	la    $t0, -20($fp)	#get address of l20
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t0, 0($t1)	#ASSIGN
	lw    $t0, -4($fp)	#get value of l8
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t1, 0
	beq   $t0, $t1, .L8		#evaluate if condition
	.data
.L9:	.asciiz "\nl8 is true \n"	# store stringLit
	.text
	la    $t0, .L9
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 4
	syscall
.L8:		# if condition false
	.data
.L10:	.asciiz "string"	# store stringLit
	.text
	la    $t0, .L10
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	.text
	la    $t0, .L10
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	beq   $t0, $t1, .L11		#operation ==
	li    $t0, 0		#not equal
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L12
.L11:
	li    $t0, 1		#equal
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L12:
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t1, 0
	beq   $t0, $t1, .L13		#evaluate if condition
	.data
.L15:	.asciiz "string true"	# store stringLit
	.text
	la    $t0, .L15
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 4
	syscall
	li    $t0, 10		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $v0, 4($sp)	#POP
	addu  $sp, $sp, 4
	j     _f1_Exit		#return statement, jump to function exit
	j     .L14		#jump to end of if-else code
.L13:		# else code
	.data
.L16:	.asciiz "string false"	# store stringLit
	.text
	la    $t0, .L16
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 4
	syscall
.L14:		# end of if-else code
_f1_Exit:
	lw    $ra, -8($fp)
	move  $t0, $fp		#save control link
	lw    $fp, -12($fp)	#restore FP
	move  $sp, $t0		#restore SP
	jr    $ra
	.text
_f2:	
	sw    $ra, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	sw    $fp, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	addu  $fp, $sp, 8		#set the FP for this function
	subu  $sp, $sp, 0		#push space for locals
	li    $t0, 0		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $v0, 4($sp)	#POP
	addu  $sp, $sp, 4
	j     _f2_Exit		#return statement, jump to function exit
_f2_Exit:
	lw    $ra, 0($fp)
	move  $t0, $fp		#save control link
	lw    $fp, -4($fp)	#restore FP
	move  $sp, $t0		#restore SP
	jr    $ra
	.text
	.globl main
main:	
__start:		# add __start label for main only
	sw    $ra, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	sw    $fp, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	addu  $fp, $sp, 8		#set the FP for this function
	subu  $sp, $sp, 12		#push space for locals
	li    $t0, 3		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	jal   _f1
	sw    $v0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	la    $t0, -8($fp)	#get address of l8
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t0, 0($t1)	#ASSIGN
	la    $t0, -8($fp)	#get address of l8
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 0($t0)	#put value into T1
	move  $a0, $t1		#put value to A0
	li    $v0, 1
	syscall
	jal   _f2
	sw    $v0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	.data
.L17:	.asciiz "\n=====start of a bunch of testing======\n"	# store stringLit
	.text
	la    $t0, .L17
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 4
	syscall
	.text
	la    $t0, .L10
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	.text
	la    $t0, .L10
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	bne   $t0, $t1, .L18		#operation !=
	li    $t0, 0		#equal
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L19
.L18:
	li    $t0, 1		#not equal
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L19:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	li    $t0, 3		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	la    $t0, -16($fp)	#get address of temp
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t0, 0($t1)	#ASSIGN
	la    $t0, -16($fp)	#get address of temp
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 0($t1)
	subu  $t0, $t0, 1		#post increment temp
	sw    $t0, 0($t1)
	la    $t0, -16($fp)	#get address of temp
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 0($t0)	#put value into T1
	move  $a0, $t1		#put value to A0
	li    $v0, 1
	syscall
	la    $t0, -16($fp)	#get address of temp
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 0($t1)
	addu  $t0, $t0, 1		#post increment temp
	sw    $t0, 0($t1)
	la    $t0, -16($fp)	#get address of temp
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 0($t0)	#put value into T1
	move  $a0, $t1		#put value to A0
	li    $v0, 1
	syscall
	lw    $t0, -16($fp)	#get value of temp
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, -16($fp)	#get value of temp
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	add   $t0, $t0, $t1		#operation +
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	lw    $t0, -16($fp)	#get value of temp
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, -16($fp)	#get value of temp
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	subu  $t0, $t0, $t1		#operation -
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	lw    $t0, -16($fp)	#get value of temp
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, -16($fp)	#get value of temp
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	div   $t0, $t1		#operation /
	mflo  $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	lw    $t0, -16($fp)	#get value of temp
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, -16($fp)	#get value of temp
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	mult  $t0, $t1		#operation *
	mflo  $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t0, 0		#load 0 for not
	seq   $t0, $t0, $t1		#operation !
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	beq   $t0, 0, .L20
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L21
.L20:
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L21:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	li    $t0, 0		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	beq   $t0, 0, .L22
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L23
.L22:
	li    $t0, 0		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L23:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
_main_Exit:
	lw    $ra, 0($fp)
	move  $t0, $fp		#save control link
	lw    $fp, -4($fp)	#restore FP
	move  $sp, $t0		#restore SP
	li    $v0, 10		#load exit code for syscall
	syscall		#only do this for main
