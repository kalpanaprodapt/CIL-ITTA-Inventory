import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { TOPOLOGY_NODE_CRITICALITY_OPTIONS, TopologyNodeCriticality } from './types';
import { validateTopologyNode, cleanTopologyNodeValues } from './schema';
import { TOPOLOGYNODE_DEFAULTS } from './types';
import type { TopologyNodeFormValues, TopologyNodeInput } from './types';

interface Props {
  initial?: TopologyNodeFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: TopologyNodeInput) => void;
  onCancel?: () => void;
}

export function TopologyNodeForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<TopologyNodeFormValues>({ ...TOPOLOGYNODE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof TopologyNodeFormValues>(key: K, value: TopologyNodeFormValues[K]) {
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
    const result = validateTopologyNode(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanTopologyNodeValues(values as Record<string, unknown>) as unknown as TopologyNodeInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Topology Node form">
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
      <FormField label="View Id" htmlFor="viewId" error={errors.viewId} required={true}>
        <Input
          id="viewId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.viewId ?? ''}
          onChange={(v) => set('viewId', v)}
        />
      </FormField>
      <FormField label="Entity Ref" htmlFor="entityRef" error={errors.entityRef} required={true}>
        <Input
          id="entityRef"
          value={values.entityRef ?? ''}
          maxLength={255}
          onChange={(v) => set('entityRef', v)}
        />
      </FormField>
      <FormField label="Node Type" htmlFor="nodeType" error={errors.nodeType} required={false}>
        <Input
          id="nodeType"
          value={values.nodeType ?? ''}
          maxLength={255}
          onChange={(v) => set('nodeType', v)}
        />
      </FormField>
      <FormField label="X Pos" htmlFor="xPos" error={errors.xPos} required={false}>
        <NumberInput
          id="xPos"
          value={values.xPos}
          step={0.0001}
          onChange={(v) => set('xPos', v)}
        />
      </FormField>
      <FormField label="Y Pos" htmlFor="yPos" error={errors.yPos} required={false}>
        <NumberInput
          id="yPos"
          value={values.yPos}
          step={0.0001}
          onChange={(v) => set('yPos', v)}
        />
      </FormField>
      <FormField label="Layer" htmlFor="layer" error={errors.layer} required={false}>
        <NumberInput
          id="layer"
          value={values.layer}
          step={1}
          onChange={(v) => set('layer', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={TOPOLOGY_NODE_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as TopologyNodeCriticality | undefined)}
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
