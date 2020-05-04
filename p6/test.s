	.data
	.align 2
_a:	.space 4	# Global var a
	.text
_f:	
	sw    $ra, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	sw    $fp, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	addu  $fp, $sp, 12		#set the FP for this function
	subu  $sp, $sp, 0		#push space for locals
	la    $t0, 0($fp)	#get address of a
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 0($t1)
	addu  $t0, $t0, 1		#post increment a
	sw    $t0, 0($t1)
	li    $t0, 0		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t1, 0
	beq   $t0, $t1, .L0		#evaluate if condition
	li    $t0, 2		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t0, 0		#load 0 for unary minus
	sub   $t0, $t0, $t1		#operation unary -
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $v0, 4($sp)	#POP
	addu  $sp, $sp, 4
	j     _f_Exit		#return statement, jump to function exit
	j     .L1		#jump to end of if-else code
.L0:		# else code
	lw    $t0, 0($fp)	#get value of a
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $v0, 4($sp)	#POP
	addu  $sp, $sp, 4
	j     _f_Exit		#return statement, jump to function exit
.L1:		# end of if-else code
_f_Exit:
	lw    $ra, -4($fp)
	move  $t0, $fp		#save control link
	lw    $fp, -8($fp)	#restore FP
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
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t1, 0
	beq   $t0, $t1, .L2		#evaluate while condition
	li    $t0, 5		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $v0, 4($sp)	#POP
	addu  $sp, $sp, 4
	j     _f2_Exit		#return statement, jump to function exit
.L2:		# while condition false
	li    $t0, 6		#load intlit into TO
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
	addu  $fp, $sp, 12		#set the FP for this function
	subu  $sp, $sp, 12		#push space for locals
	li    $t0, 11		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	jal   _f
	sw    $v0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	jal   _f2
	sw    $v0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
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
