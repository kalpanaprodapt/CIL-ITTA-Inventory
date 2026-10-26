import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { IP_ADDRESS_ASSIGNMENT_TYPE_OPTIONS, IpAddressAssignmentType } from './types';
import { validateIpAddress, cleanIpAddressValues } from './schema';
import { IPADDRESS_DEFAULTS } from './types';
import type { IpAddressFormValues, IpAddressInput } from './types';

interface Props {
  initial?: IpAddressFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: IpAddressInput) => void;
  onCancel?: () => void;
}

export function IpAddressForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<IpAddressFormValues>({ ...IPADDRESS_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof IpAddressFormValues>(key: K, value: IpAddressFormValues[K]) {
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
    const result = validateIpAddress(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanIpAddressValues(values as Record<string, unknown>) as unknown as IpAddressInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Ip Address form">
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
      <FormField label="Subnet Id" htmlFor="subnetId" error={errors.subnetId} required={true}>
        <Input
          id="subnetId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.subnetId ?? ''}
          onChange={(v) => set('subnetId', v)}
        />
      </FormField>
      <FormField label="Address" htmlFor="address" error={errors.address} required={true}>
        <Input
          id="address"
          value={values.address ?? ''}
          maxLength={255}
          onChange={(v) => set('address', v)}
        />
      </FormField>
      <FormField label="Assigned To Id" htmlFor="assignedToId" error={errors.assignedToId} required={false}>
        <Input
          id="assignedToId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.assignedToId ?? ''}
          onChange={(v) => set('assignedToId', v)}
        />
      </FormField>
      <FormField label="Assignment Type" htmlFor="assignmentType" error={errors.assignmentType} required={false}>
        <Select
          id="assignmentType"
          value={values.assignmentType ?? ''}
          options={IP_ADDRESS_ASSIGNMENT_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('assignmentType', (v || undefined) as IpAddressAssignmentType | undefined)}
        />
      </FormField>
      <FormField label="Dns Name" htmlFor="dnsName" error={errors.dnsName} required={false}>
        <Input
          id="dnsName"
          value={values.dnsName ?? ''}
          maxLength={255}
          onChange={(v) => set('dnsName', v)}
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
