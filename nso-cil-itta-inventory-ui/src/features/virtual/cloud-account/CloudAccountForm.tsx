import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { CLOUD_ACCOUNT_PROVIDER_OPTIONS, CLOUD_ACCOUNT_LIFECYCLE_STAGE_OPTIONS, CloudAccountProvider, CloudAccountLifecycleStage } from './types';
import { validateCloudAccount, cleanCloudAccountValues } from './schema';
import { CLOUDACCOUNT_DEFAULTS } from './types';
import type { CloudAccountFormValues, CloudAccountInput } from './types';

interface Props {
  initial?: CloudAccountFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: CloudAccountInput) => void;
  onCancel?: () => void;
}

export function CloudAccountForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<CloudAccountFormValues>({ ...CLOUDACCOUNT_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof CloudAccountFormValues>(key: K, value: CloudAccountFormValues[K]) {
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
    const result = validateCloudAccount(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanCloudAccountValues(values as Record<string, unknown>) as unknown as CloudAccountInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Cloud Account form">
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
      <FormField label="Provider" htmlFor="provider" error={errors.provider} required={false}>
        <Select
          id="provider"
          value={values.provider ?? ''}
          options={CLOUD_ACCOUNT_PROVIDER_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('provider', (v || undefined) as CloudAccountProvider | undefined)}
        />
      </FormField>
      <FormField label="Account Ref" htmlFor="accountRef" error={errors.accountRef} required={true}>
        <Input
          id="accountRef"
          value={values.accountRef ?? ''}
          maxLength={255}
          onChange={(v) => set('accountRef', v)}
        />
      </FormField>
      <FormField label="Subscription Name" htmlFor="subscriptionName" error={errors.subscriptionName} required={false}>
        <Input
          id="subscriptionName"
          value={values.subscriptionName ?? ''}
          maxLength={255}
          onChange={(v) => set('subscriptionName', v)}
        />
      </FormField>
      <FormField label="Monthly Budget" htmlFor="monthlyBudget" error={errors.monthlyBudget} required={false}>
        <NumberInput
          id="monthlyBudget"
          value={values.monthlyBudget}
          step={0.0001}
          onChange={(v) => set('monthlyBudget', v)}
        />
      </FormField>
      <FormField label="Environment" htmlFor="environment" error={errors.environment} required={false}>
        <Input
          id="environment"
          value={values.environment ?? ''}
          maxLength={255}
          onChange={(v) => set('environment', v)}
        />
      </FormField>
      <FormField label="Notes" htmlFor="notes" error={errors.notes} required={false}>
        <TextArea
          id="notes"
          value={values.notes ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('notes', v)}
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
          options={CLOUD_ACCOUNT_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as CloudAccountLifecycleStage | undefined)}
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
