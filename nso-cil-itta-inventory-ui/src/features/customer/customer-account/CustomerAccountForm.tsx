import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { CUSTOMER_ACCOUNT_BILLING_CYCLE_OPTIONS, CUSTOMER_ACCOUNT_LIFECYCLE_STAGE_OPTIONS, CustomerAccountBillingCycle, CustomerAccountLifecycleStage } from './types';
import { validateCustomerAccount, cleanCustomerAccountValues } from './schema';
import { CUSTOMERACCOUNT_DEFAULTS } from './types';
import type { CustomerAccountFormValues, CustomerAccountInput } from './types';

interface Props {
  initial?: CustomerAccountFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: CustomerAccountInput) => void;
  onCancel?: () => void;
}

export function CustomerAccountForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<CustomerAccountFormValues>({ ...CUSTOMERACCOUNT_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof CustomerAccountFormValues>(key: K, value: CustomerAccountFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validateCustomerAccount(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanCustomerAccountValues(values as Record<string, unknown>) as unknown as CustomerAccountInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Customer Account form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="Customer Id" htmlFor="customerId" error={errors.customerId} required={true}>
        <Input
          id="customerId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.customerId ?? ''}
          onChange={(v) => set('customerId', v)}
        />
      </FormField>
      <FormField label="Account Number" htmlFor="accountNumber" error={errors.accountNumber} required={true}>
        <Input
          id="accountNumber"
          value={values.accountNumber ?? ''}
          maxLength={255}
          onChange={(v) => set('accountNumber', v)}
        />
      </FormField>
      <FormField label="Billing Cycle" htmlFor="billingCycle" error={errors.billingCycle} required={false}>
        <Select
          id="billingCycle"
          value={values.billingCycle ?? ''}
          options={CUSTOMER_ACCOUNT_BILLING_CYCLE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('billingCycle', (v || undefined) as CustomerAccountBillingCycle | undefined)}
        />
      </FormField>
      <FormField label="Currency" htmlFor="currency" error={errors.currency} required={false}>
        <Input
          id="currency"
          value={values.currency ?? ''}
          maxLength={255}
          onChange={(v) => set('currency', v)}
        />
      </FormField>
      <FormField label="Credit Limit" htmlFor="creditLimit" error={errors.creditLimit} required={false}>
        <NumberInput
          id="creditLimit"
          value={values.creditLimit}
          step={0.0001}
          onChange={(v) => set('creditLimit', v)}
        />
      </FormField>
      <FormField label="Effective From" htmlFor="effectiveFrom" error={errors.effectiveFrom} required={false}>
        <DateInput
          id="effectiveFrom"
          value={values.effectiveFrom ?? ''}
          onChange={(v) => set('effectiveFrom', v)}
        />
      </FormField>
      <FormField label="Effective To" htmlFor="effectiveTo" error={errors.effectiveTo} required={false}>
        <DateInput
          id="effectiveTo"
          value={values.effectiveTo ?? ''}
          onChange={(v) => set('effectiveTo', v)}
        />
      </FormField>
      <FormField label="Labels" htmlFor="labels" error={errors.labels} required={false}>
        <Input
          id="labels"
          value={values.labels ?? ''}
          maxLength={255}
          onChange={(v) => set('labels', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={CUSTOMER_ACCOUNT_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as CustomerAccountLifecycleStage | undefined)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}
