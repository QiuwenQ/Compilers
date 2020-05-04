	.data
	.align 2
_a:	.space 4	# Global var a
	.text
	.globl main
main:	
__start:		# add __start label for main only
	sw    $ra, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	sw    $fp, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	addu  $fp, $sp, 12		#set the FP for this function
	subu  $sp, $sp, 12		#push space for locals
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	la    $t0, -16($fp)	#get address of localm16
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t0, 0($t1)	#ASSIGN
	li    $t0, 0		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	la    $t0, -20($fp)	#get address of localm20
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t0, 0($t1)	#ASSIGN
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	la    $t0, _a		#get address global var a
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t0, 0($t1)	#ASSIGN
	li    $t0, 2		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	la    $t0, -12($fp)	#get address of localm12
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t0, 0($t1)	#ASSIGN
	lw    $t0, _a		#get global var a
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, _a		#get global var a
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	beq   $t0, $t1, .L0		#operation ==
	li    $t0, 0		#not equal
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L1
.L0:
	li    $t0, 1		#equal
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L1:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	lw    $t0, -12($fp)	#get value of localm12
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, _a		#get global var a
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	beq   $t0, $t1, .L2		#operation ==
	li    $t0, 0		#not equal
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L3
.L2:
	li    $t0, 1		#equal
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L3:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	.data
.L4:	.asciiz "hi"	# store stringLit
	.text
	la    $t0, .L4
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	.text
	la    $t0, .L4
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	beq   $t0, $t1, .L5		#operation ==
	li    $t0, 0		#not equal
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L6
.L5:
	li    $t0, 1		#equal
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L6:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	.text
	la    $t0, .L4
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	.data
.L7:	.asciiz "bye"	# store stringLit
	.text
	la    $t0, .L7
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	beq   $t0, $t1, .L8		#operation ==
	li    $t0, 0		#not equal
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L9
.L8:
	li    $t0, 1		#equal
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L9:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
_main_Exit:
	lw    $ra, -4($fp)
	move  $t0, $fp		#save control link
	lw    $fp, -8($fp)	#restore FP
	move  $sp, $t0		#restore SP
	li    $v0, 10		#load exit code for syscall
	syscall		#only do this for main
