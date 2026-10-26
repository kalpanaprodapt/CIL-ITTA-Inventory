import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { DNS_RECORD_RECORD_TYPE_OPTIONS, DNS_RECORD_CRITICALITY_OPTIONS, DnsRecordRecordType, DnsRecordCriticality } from './types';
import { validateDnsRecord, cleanDnsRecordValues } from './schema';
import { DNSRECORD_DEFAULTS } from './types';
import type { DnsRecordFormValues, DnsRecordInput } from './types';

interface Props {
  initial?: DnsRecordFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: DnsRecordInput) => void;
  onCancel?: () => void;
}

export function DnsRecordForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<DnsRecordFormValues>({ ...DNSRECORD_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof DnsRecordFormValues>(key: K, value: DnsRecordFormValues[K]) {
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
    const result = validateDnsRecord(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanDnsRecordValues(values as Record<string, unknown>) as unknown as DnsRecordInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Dns Record form">
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
      <FormField label="Zone Name" htmlFor="zoneName" error={errors.zoneName} required={true}>
        <Input
          id="zoneName"
          value={values.zoneName ?? ''}
          maxLength={255}
          onChange={(v) => set('zoneName', v)}
        />
      </FormField>
      <FormField label="Record Type" htmlFor="recordType" error={errors.recordType} required={false}>
        <Select
          id="recordType"
          value={values.recordType ?? ''}
          options={DNS_RECORD_RECORD_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('recordType', (v || undefined) as DnsRecordRecordType | undefined)}
        />
      </FormField>
      <FormField label="Record Value" htmlFor="recordValue" error={errors.recordValue} required={true}>
        <Input
          id="recordValue"
          value={values.recordValue ?? ''}
          maxLength={255}
          onChange={(v) => set('recordValue', v)}
        />
      </FormField>
      <FormField label="Ttl" htmlFor="ttl" error={errors.ttl} required={false}>
        <NumberInput
          id="ttl"
          value={values.ttl}
          step={1}
          onChange={(v) => set('ttl', v)}
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
          options={DNS_RECORD_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as DnsRecordCriticality | undefined)}
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
