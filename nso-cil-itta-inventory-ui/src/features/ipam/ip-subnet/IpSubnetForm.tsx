import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { IP_SUBNET_IP_VERSION_OPTIONS, IP_SUBNET_CRITICALITY_OPTIONS, IpSubnetIpVersion, IpSubnetCriticality } from './types';
import { validateIpSubnet, cleanIpSubnetValues } from './schema';
import { IPSUBNET_DEFAULTS } from './types';
import type { IpSubnetFormValues, IpSubnetInput } from './types';

interface Props {
  initial?: IpSubnetFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: IpSubnetInput) => void;
  onCancel?: () => void;
}

export function IpSubnetForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<IpSubnetFormValues>({ ...IPSUBNET_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof IpSubnetFormValues>(key: K, value: IpSubnetFormValues[K]) {
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
    const result = validateIpSubnet(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanIpSubnetValues(values as Record<string, unknown>) as unknown as IpSubnetInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Ip Subnet form">
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
      <FormField label="Cidr" htmlFor="cidr" error={errors.cidr} required={true}>
        <Input
          id="cidr"
          value={values.cidr ?? ''}
          maxLength={255}
          onChange={(v) => set('cidr', v)}
        />
      </FormField>
      <FormField label="Ip Version" htmlFor="ipVersion" error={errors.ipVersion} required={false}>
        <Select
          id="ipVersion"
          value={values.ipVersion ?? ''}
          options={IP_SUBNET_IP_VERSION_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('ipVersion', (v || undefined) as IpSubnetIpVersion | undefined)}
        />
      </FormField>
      <FormField label="Vrf Id" htmlFor="vrfId" error={errors.vrfId} required={false}>
        <Input
          id="vrfId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.vrfId ?? ''}
          onChange={(v) => set('vrfId', v)}
        />
      </FormField>
      <FormField label="Gateway" htmlFor="gateway" error={errors.gateway} required={false}>
        <Input
          id="gateway"
          value={values.gateway ?? ''}
          maxLength={255}
          onChange={(v) => set('gateway', v)}
        />
      </FormField>
      <FormField label="Vlan Id" htmlFor="vlanId" error={errors.vlanId} required={false}>
        <Input
          id="vlanId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.vlanId ?? ''}
          onChange={(v) => set('vlanId', v)}
        />
      </FormField>
      <FormField label="Utilization Pct" htmlFor="utilizationPct" error={errors.utilizationPct} required={false}>
        <NumberInput
          id="utilizationPct"
          value={values.utilizationPct}
          step={0.0001}
          onChange={(v) => set('utilizationPct', v)}
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
          options={IP_SUBNET_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as IpSubnetCriticality | undefined)}
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
