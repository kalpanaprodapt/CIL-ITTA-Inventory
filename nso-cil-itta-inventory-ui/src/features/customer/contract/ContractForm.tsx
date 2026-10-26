import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { CONTRACT_CRITICALITY_OPTIONS, ContractCriticality } from './types';
import { validateContract, cleanContractValues } from './schema';
import { CONTRACT_DEFAULTS } from './types';
import type { ContractFormValues, ContractInput } from './types';

interface Props {
  initial?: ContractFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: ContractInput) => void;
  onCancel?: () => void;
}

export function ContractForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<ContractFormValues>({ ...CONTRACT_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof ContractFormValues>(key: K, value: ContractFormValues[K]) {
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
    const result = validateContract(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanContractValues(values as Record<string, unknown>) as unknown as ContractInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Contract form">
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
      <FormField label="Contract Number" htmlFor="contractNumber" error={errors.contractNumber} required={true}>
        <Input
          id="contractNumber"
          value={values.contractNumber ?? ''}
          maxLength={255}
          onChange={(v) => set('contractNumber', v)}
        />
      </FormField>
      <FormField label="Start Date" htmlFor="startDate" error={errors.startDate} required={true}>
        <DateInput
          id="startDate"
          value={values.startDate ?? ''}
          onChange={(v) => set('startDate', v)}
        />
      </FormField>
      <FormField label="End Date" htmlFor="endDate" error={errors.endDate} required={false}>
        <DateInput
          id="endDate"
          value={values.endDate ?? ''}
          onChange={(v) => set('endDate', v)}
        />
      </FormField>
      <FormField label="Term Months" htmlFor="termMonths" error={errors.termMonths} required={false}>
        <NumberInput
          id="termMonths"
          value={values.termMonths}
          step={1}
          onChange={(v) => set('termMonths', v)}
        />
      </FormField>
      <FormField label="Auto Renew" htmlFor="autoRenew" error={errors.autoRenew} required={false}>
        <Checkbox
          id="autoRenew"
          checked={Boolean(values.autoRenew)}
          onChange={(v) => set('autoRenew', v)}
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
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
        />
      </FormField>
      <FormField label="Owner" htmlFor="owner" error={errors.owner} required={false}>
        <Input
          id="owner"
          value={values.owner ?? ''}
          maxLength={255}
          onChange={(v) => set('owner', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={CONTRACT_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as ContractCriticality | undefined)}
        />
      </FormField>
      <FormField label="Cost Center" htmlFor="costCenter" error={errors.costCenter} required={false}>
        <Input
          id="costCenter"
          value={values.costCenter ?? ''}
          maxLength={255}
          onChange={(v) => set('costCenter', v)}
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
