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
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 2		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	blt   $t0, $t1, .L0		#operation <
	li    $t0, 0		#not less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L1
.L0:
	li    $t0, 1		#less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L1:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	lw    $t0, -12($fp)	#get value of localm12
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	blt   $t0, $t1, .L2		#operation <
	li    $t0, 0		#not less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L3
.L2:
	li    $t0, 1		#less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L3:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	li    $t0, 2		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	bgt   $t0, $t1, .L4		#operation >
	li    $t0, 0		#not less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L5
.L4:
	li    $t0, 1		#less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L5:
	lw    $a0, 4($sp)	#POP value for write stmt
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
	add   $t0, $t0, $t1		#operation +
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 3		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	bgt   $t0, $t1, .L6		#operation >
	li    $t0, 0		#not less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L7
.L6:
	li    $t0, 1		#less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L7:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	li    $t0, 2		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 2		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	bge   $t0, $t1, .L8		#operation >=
	li    $t0, 0		#not less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L9
.L8:
	li    $t0, 1		#less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L9:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	li    $t0, 3		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 2		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	bge   $t0, $t1, .L10		#operation >=
	li    $t0, 0		#not less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L11
.L10:
	li    $t0, 1		#less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L11:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	li    $t0, 2		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 3		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	bge   $t0, $t1, .L12		#operation >=
	li    $t0, 0		#not less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L13
.L12:
	li    $t0, 1		#less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L13:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	li    $t0, 2		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 2		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	ble   $t0, $t1, .L14		#operation <=
	li    $t0, 0		#not less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L15
.L14:
	li    $t0, 1		#less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L15:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 2		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	ble   $t0, $t1, .L16		#operation <=
	li    $t0, 0		#not less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L17
.L16:
	li    $t0, 1		#less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L17:
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	li    $t0, 3		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 2		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	ble   $t0, $t1, .L18		#operation <=
	li    $t0, 0		#not less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	j     .L19
.L18:
	li    $t0, 1		#less than
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L19:
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
