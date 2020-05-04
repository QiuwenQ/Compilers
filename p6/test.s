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
	lw    $t0, -20($fp)	#get value of localm20
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	beq   $t0, 0, .L0
	lw    $t0, -16($fp)	#get value of localm16
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L1
.L0:
	lw    $t0, -20($fp)	#get value of localm20
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L1:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	lw    $t0, -16($fp)	#get value of localm16
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	beq   $t0, 0, .L2
	lw    $t0, -16($fp)	#get value of localm16
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L3
.L2:
	lw    $t0, -16($fp)	#get value of localm16
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L3:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	lw    $t0, -16($fp)	#get value of localm16
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	beq   $t0, 1, .L4
	lw    $t0, -20($fp)	#get value of localm20
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L5
.L4:
	lw    $t0, -16($fp)	#get value of localm16
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L5:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	lw    $t0, -20($fp)	#get value of localm20
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	beq   $t0, 1, .L6
	lw    $t0, -20($fp)	#get value of localm20
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L7
.L6:
	lw    $t0, -20($fp)	#get value of localm20
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L7:
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
